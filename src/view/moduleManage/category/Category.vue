<template>
  <el-card class="category-container">
    <template #header>
      <div class="header">
        <el-button type="primary" :icon="Plus" @click="handleAdd">增加</el-button>
        <el-popconfirm
            title="确定删除吗？"
            confirmButtonText='确定'
            cancelButtonText='取消'
            @confirm="handleDelete">
          <template #reference>
            <el-button type="danger" :icon="Delete">批量删除</el-button>
          </template>
        </el-popconfirm>
      </div>
    </template>
    <el-table
        :load="state.loading"
        ref="multipleTable"
        :data="state.tableData"
        tooltip-effect="dark"
        style="width: 100%"
        @selection-change="handleSelectionChange">
      <el-table-column
          type="selection"
          width="55"
      >
      </el-table-column>
      <el-table-column
          prop="categoryName"
          label="分类名称"
      >
      </el-table-column>
      <el-table-column
          prop="categoryRank"
          label="排序值"
          width="120"
      >
      </el-table-column>
      <el-table-column
          prop="createTime"
          label="添加时间"
          width="200"
      >
      </el-table-column>
      <el-table-column
          label="操作"
          width="220"
      >
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleEdit(scope.row.categoryId)">修改
          </el-button>
          <el-button link type="primary" size="small" @click="handleNext(scope.row)">下级分类</el-button>
          <el-button link type="danger" size="small" @click="handleDeleteOne(scope.row.categoryId)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!--总数超过一页，再展示分页器-->
    <el-pagination
        background
        layout="prev, pager, next"
        :total="state.total"
        :page-size="state.pageSize"
        :current-page="state.currentPage"
        @current-change="changePage"
    />
    <DialogAddCategory ref='addCate' :reload="getCategory" :type="state.type"/>
  </el-card>
</template>

<script setup>
import {onMounted, onUnmounted, reactive, ref, watchEffect} from 'vue'
import {useRoute, useRouter} from 'vue-router'
import {ElMessage, ElMessageBox} from 'element-plus'
import {Delete, Plus} from '@element-plus/icons-vue'
import axiosInstance from '../../../utils/http/axios.js'
import DialogAddCategory from './DialogAddCategory.vue'

const addCate = ref(null)
const router = useRouter() // 声明路由实例
const route = useRoute() // 获取路由参数
const state = reactive({
  loading: false,
  tableData: [], // 数据列表
  multipleSelection: [], // 选中项
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 3, // 分页大小
  type: 'add', // 操作类型
  level: 1,
  parent_id: 0
})

onMounted(() => {
  getCategory()
})

watchEffect(() => {
  console.log(state.pageSize)
})

const unwatch = router.afterEach((to) => {
  // 每次路由变化的时候，都会触发监听时间，重新获取列表数据
  if (['category', 'level2', 'level3'].includes(to.name)) {
    getCategory()
  }
})

onUnmounted(() => {
  unwatch()
})

// 获取分类列表
const getCategory = () => {
  const {level = 1, parent_id = 0} = route.query
  state.loading = true
  axiosInstance.get('/categories', {
    params: {
      pageNumber: state.currentPage,
      pageSize: state.pageSize,
      categoryLevel: level,
      parentId: parent_id
    }
  }).then(res => {
    state.tableData = res.data.list
    state.total = res.data.totalCount
    state.currentPage = res.data.currPage
    state.loading = false
    state.level = level
    state.parentId = parent_id
  })
}

const changePage = (val) => {
  state.currentPage = val
  getCategory()
}

const handleNext = (item) => {
  const levelNumber = item.categoryLevel + 1
  if (levelNumber == 4) {
    ElMessage.error('没有下一级')
    return
  }
  router.push({
    name: `level${levelNumber}`,
    query: {
      level: levelNumber,
      parent_id: item.categoryId
    }
  })
}

// 添加分类
const handleAdd = () => {
  state.type = 'add'
  // 这里ref 的作用是调用子组件的方法
  addCate.value.open()
}

// 修改分类
const handleEdit = (id) => {
  state.type = 'edit'
  addCate.value.open(id)
}
// 选择项
const handleSelectionChange = (val) => {
  state.multipleSelection = val
}
// 批量删除
const handleDelete = () => {
  if (!state.multipleSelection.length) {
    ElMessage.error('请选择项')
    return
  }
  axiosInstance.delete('/categories', {
    data: {
      ids: state.multipleSelection.map(i => i.categoryId)
    }
  }).then(() => {
    ElMessage.success('删除成功')
    getCategory()
  })
}

// 单个删除
const handleDeleteOne = (id) => {
  ElMessageBox.confirm(
      '确定要删除该用户信息吗?',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    const res = await axiosInstance.delete('/categories', {
      data: {
        ids: [id]
      }
    });

    if (res.resultCode === 200) {
      ElMessage.success("删除成功");
      getCategory();
    } else {
      ElMessage.error("删除失败")
    }

  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消删除',
    })
  })
}
</script>

<style>

</style>

<template>
  <el-card class="index-container">
    <template #header>
      <div class="header">
        <el-button type="primary" :icon="Plus" @click="handleAdd">增加</el-button>
        <el-popconfirm
            title="确定删除吗？"
            confirmButtonText='确定'
            cancelButtonText='取消'
            @confirm="handleDelete"
        >
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
          width="55">
      </el-table-column>
      <el-table-column
          prop="configName"
          label="商品名称"
      >
      </el-table-column>
      <el-table-column
          label="跳转链接"
      >
        <template #default="scope">
          <a target="_blank" :href="scope.row.redirectUrl">{{ scope.row.redirectUrl }}</a>
        </template>
      </el-table-column>
      <el-table-column
          prop="configRank"
          label="排序值"
          width="120"
      >
      </el-table-column>
      <el-table-column
          prop="goodsId"
          label="商品编号"
          width="200"
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
          width="100">
        <template #default="scope">
          <a style="cursor: pointer; margin-right: 10px" @click="handleEdit(scope.row.configId)">修改</a>
          <el-popconfirm
              title="确定删除吗？"
              confirmButtonText='确定'
              cancelButtonText='取消'
              @confirm="handleDeleteOne(scope.row.configId)">
            <template #reference>
              <a style="cursor: pointer">删除</a>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <!--总数超过一页，再展示分页器-->
    <el-pagination background style="margin-top:20px" :current-page="state.currentPage" :page-size="state.pageSize"
                   :page-sizes="[3, 5, 10, 20]" layout="->,total, sizes, prev, pager, next, jumper"
                   :total="state.total"
                   @size-change="sizePage" @current-change="changePage"/>
  </el-card>
  <DialogAddGood ref='addGood' :reload="getIndexConfig" :type="state.type" :configType="state.configType"/>
</template>

<script setup>
import {onMounted, reactive, ref} from 'vue'
import {ElMessage} from 'element-plus'
import {Delete, Plus} from '@element-plus/icons-vue'
import DialogAddGood from '../product/DialogAddGood.vue'
import {useRoute, useRouter} from 'vue-router'
import axiosInstance from '../../../utils/http/axios.js'
// 首页配置类型参数
const configTypeMap = {
  hot: 3,
  new: 4,
  recommend: 5
}

const router = useRouter()
const route = useRoute()
const multipleTable = ref(null)
const addGood = ref(null)
const state = reactive({
  loading: false,
  tableData: [], // 数据列表
  multipleSelection: [], // 选中项
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 3, // 分页大小
  type: 'add', // 操作类型
  configType: 3 // 3-(首页)热销商品 4-(首页)新品上线 5-(首页)为你推荐
})
// 监听路由变化
router.beforeEach((to) => {
  if (['hot', 'new', 'recommend'].includes(to.name)) {
    state.configType = configTypeMap[to.name]
    state.currentPage = 1
    getIndexConfig()
  }
})
// 初始化
onMounted(() => {
  state.configType = configTypeMap[route.name]
  getIndexConfig()
})
// 首页热销商品列表
const getIndexConfig = () => {
  state.loading = true
  axiosInstance.get('/indexConfigs', {
    params: {
      pageNumber: state.currentPage,
      pageSize: state.pageSize,
      configType: state.configType
    }
  }).then(res => {
    console.log(res.data)
    state.tableData = res.data.list
    state.total = res.data.totalCount
    state.currentPage = res.data.currPage
    state.loading = false
  })
}
// 添加商品
const handleAdd = () => {
  state.type = 'add'
  addGood.value.open()
}
// 修改商品
const handleEdit = (id) => {
  state.type = 'edit'
  addGood.value.open(id)
}
// 选择项
const handleSelectionChange = (val) => {
  state.multipleSelection = val
}
// 删除
const handleDelete = () => {
  if (!state.multipleSelection.length) {
    ElMessage.error('请选择项')
    return
  }
  axiosInstance.delete('/indexConfigs', {
    data: {
      ids: state.multipleSelection.map(i => i.configId)
    }
  }).then(() => {
    ElMessage.success('删除成功')
    getIndexConfig()
  })
}

// 单个删除
const handleDeleteOne = (id) => {
  axiosInstance.delete('/indexConfigs', {
    data: {
      ids: [id]
    }
  }).then(() => {
    ElMessage.success('删除成功')
    getIndexConfig()
  })
}

const sizePage = (val) => {
  state.pageSize = val;
  getIndexConfig();
}

const changePage = (val) => {
  state.currentPage = val
  getIndexConfig()
}
</script>

<style scoped>
.index-container {
  min-height: 100%;
}

.el-card.is-always-shadow {
  min-height: 100% !important;
}
</style>

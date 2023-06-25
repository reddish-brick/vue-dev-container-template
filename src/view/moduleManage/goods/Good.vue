<template>
  <el-card class="good-container">
    <template #header>
      <div class="header">
        <el-button type="primary" :icon="Plus" @click="handleAdd">新增商品</el-button>
      </div>
    </template>
    <el-table
        :load="state.loading"
        :data="state.tableData"
        tooltip-effect="dark"
        style="width: 100%"
    >
      <el-table-column
          prop="goodsId"
          label="商品编号"
      >
      </el-table-column>
      <el-table-column
          prop="goodsName"
          label="商品名"
      >
      </el-table-column>
      <el-table-column
          prop="goodsIntro"
          label="商品简介"
      >
      </el-table-column>
      <el-table-column
          label="商品图片"
          width="150px"
      >
        <template #default="scope">
          <img style="width: 100px; height: 100px;" :key="scope.row.goodsId"
               :src="scope.row.goodsCoverImg" alt="商品主图">
        </template>
      </el-table-column>
      <el-table-column
          prop="stockNum"
          label="商品库存"
      >
      </el-table-column>
      <el-table-column
          prop="sellingPrice"
          label="商品售价"
      >
      </el-table-column>
      <el-table-column
          label="上架状态"
      >
        <template #default="scope">
          <span style="color: green;" v-if="scope.row.goodsSellStatus == 0">销售中</span>
          <span style="color: red;" v-else>已下架</span>
        </template>
      </el-table-column>

      <el-table-column
          label="操作"
          width="100"
      >
        <template #default="scope">
          <el-button link type="primary" size="small" @click="handleEdit(scope.row.goodsId)">修改
          </el-button>

          <el-button link type="primary" v-if="scope.row.goodsSellStatus == 0" size="small"
                     @click="handleStatus(scope.row.goodsId, 1)">下架
          </el-button>
          <el-button link type="primary" v-else size="small" @click="handleStatus(scope.row.goodsId, 0)">上架
          </el-button>
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
  </el-card>
</template>

<script setup>
import {getCurrentInstance, onMounted, reactive} from 'vue'
import axiosInstance from '../../../utils/http/axios.js'
import {ElMessage} from 'element-plus'
import {Plus} from '@element-plus/icons-vue'
import {useRouter} from 'vue-router'

const app = getCurrentInstance()
const {goTop} = app.appContext.config.globalProperties
const router = useRouter()
const state = reactive({
  loading: false,
  tableData: [], // 数据列表
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 10 // 分页大小
})
onMounted(() => {
  getGoodList()
})

// 获取轮播图列表
const getGoodList = () => {
  state.loading = true
  axiosInstance.get('/goods/list', {
    params: {
      pageNumber: state.currentPage,
      pageSize: state.pageSize
    }
  }).then(res => {
    state.tableData = res.data.list
    state.total = res.data.totalCount
    state.currentPage = res.data.currPage
    state.loading = false
    goTop && goTop()
  })
}

const handleAdd = () => {
  router.push({path: '/add_good'})
}

const handleEdit = (id) => {
  router.push({path: '/add_good', query: {id}})
}

const changePage = (val) => {
  state.currentPage = val
  getGoodList()
}

const handleStatus = (id, status) => {
  axiosInstance.put(`/goods/status/${status}`, {
    ids: id ? [id] : []
  }).then(() => {
    ElMessage.success('修改成功')
    getGoodList()
  })
}
</script>

<style scoped>
.good-container {
  min-height: 100%;
}

.el-card.is-always-shadow {
  min-height: 100% !important;
}
</style>

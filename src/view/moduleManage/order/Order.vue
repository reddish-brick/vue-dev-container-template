<template>
  <el-card class="order-container">
    <template #header>
      <div class="header">
        <el-input style="width: 200px; margin-right: 10px" placeholder="请输入订单号" v-model="state.orderNo"
                  @change="handleOption" clearable/>
        <el-select @change="handleOption" v-model="state.orderStatus" style="width: 200px; margin-right: 10px">
          <el-option v-for="item in state.options" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
        <!-- <el-button type="primary" size="small" icon="el-icon-edit">修改订单</el-button> -->
        <el-button type="primary" :icon="HomeFilled" @click="handleConfig()">配货完成</el-button>
        <el-button type="primary" :icon="HomeFilled" @click="handleSend()">出库</el-button>
        <el-button type="danger" :icon="Delete" @click="handleClose()">关闭订单</el-button>
      </div>
    </template>
    <el-table :load="state.loading" :data="state.tableData" tooltip-effect="dark" style="width: 100%"
              @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55">
      </el-table-column>
      <el-table-column prop="orderNo" label="订单号">
      </el-table-column>
      <el-table-column prop="totalPrice" label="订单总价">
      </el-table-column>
      <el-table-column prop="orderStatus" label="订单状态">
        <template #default="scope">
          <span>{{ $filters.orderMap(scope.row.orderStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="payType" label="支付方式">
        <template #default='scope'>
          <span v-if="scope.row.payType == 1">微信支付</span>
          <span v-else-if="scope.row.payType == 2">支付宝支付</span>
          <span v-else>未知</span>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间">
      </el-table-column>
      <el-table-column label="操作">
        <template #default="scope">
          <el-button v-if="scope.row.orderStatus == 1" title="确定配货完成吗？" link type="danger"
                     size="small" @click="handleConfig(scope.row.orderId)">配货完成
          </el-button>

          <el-button v-if="scope.row.orderStatus == 2" title="确定出库吗？" link type="danger"
                     size="small" @click="handleSend(scope.row.orderId)">出库
          </el-button>

          <el-button v-if="!(scope.row.orderStatus == 4 || scope.row.orderStatus < 0)" link type="danger"
                     size="small" @click="handleClose(scope.row.orderId)">关闭订单
          </el-button>

          <el-button link type="primary" size="small"
                     @click="() => router.push({ path: '/order_detail', query: { id: scope.row.orderId } })">订单详情
          </el-button>
          <!--          <router-link :to="{ path: '/order_detail', query: { id: scope.row.orderId } }">订单详情</router-link>-->
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background layout="prev, pager, next" :total="state.total" :page-size="state.pageSize"
                   :current-page="state.currentPage" @current-change="changePage"/>
  </el-card>
</template>

<script setup>
import {onMounted, reactive} from 'vue'
import {ElMessage, ElMessageBox} from 'element-plus'
import {Delete, HomeFilled} from '@element-plus/icons-vue'
import axiosInstance from '../../../utils/http/axios.js'
import {useRouter} from 'vue-router'

const router = useRouter();

const state = reactive({
  loading: false,
  tableData: [], // 数据列表
  multipleSelection: [], // 选中项
  total: 0, // 总条数
  currentPage: 1, // 当前页
  pageSize: 3, // 分页大小
  orderNo: '', // 订单号
  orderStatus: '', // 订单状态
  // 订单状态筛选项默认值
  options: [
    {
      value: '',
      label: '全部'
    },
    {
      value: 0,
      label: '待支付'
    },
    {
      value: 1,
      label: '已支付'
    },
    {
      value: 2,
      label: '配货完成'
    },
    {
      value: 3,
      label: '出库成功'
    },
    {
      value: 4,
      label: '交易成功'
    },
    {
      value: -1,
      label: '手动关闭'
    },
    {
      value: -2,
      label: '超时关闭'
    },
    {
      value: -3,
      label: '商家关闭'
    }
  ]
})

// 初始化获取订单列表
onMounted(() => {
  getOrderList()
})

// 获取列表方法
const getOrderList = () => {
  state.loading = true
  axiosInstance.get('/orders', {
    params: {
      pageNumber: state.currentPage,
      pageSize: state.pageSize,
      orderNo: state.orderNo,
      orderStatus: state.orderStatus
    }
  }).then(res => {
    console.log(res.data.list);
    state.tableData = res.data.list
    state.total = res.data.totalCount
    state.currentPage = res.data.currPage
    state.loading = false
  })
}
// 触发过滤项方法
const handleOption = () => {
  state.currentPage = 1
  getOrderList()
}
// checkbox 选择项
const handleSelectionChange = (val) => {
  state.multipleSelection = val
}
// 翻页方法
const changePage = (val) => {
  state.currentPage = val
  getOrderList()
}

// 配货方法
const handleConfig = (id) => {
  let params;
  // 当个配置
  if (id) {
    params = [id];
  } else {
    if (!state.multipleSelection.length) {
      console.log('state.multipleSelection', state.multipleSelection.length);
      ElMessage.error('请选择项');
      return;
    }
    // 多选配置
    params = state.multipleSelection.map(i => i.orderId);
  }

  ElMessageBox.confirm(
      '确定配货完成吗？',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    const res = await axiosInstance.put('/orders/checkDone', {
      ids: params
    });

    if (res.resultCode === 200) {
      ElMessage.success("配货完成成功");
      getOrderList()
    } else {
      ElMessage.error("配货完成失败")
    }

  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消配货完成',
    })
  })
}

// 出库方法
const handleSend = (id) => {
  let params;
  if (id) {
    params = [id];
  } else {
    if (!state.multipleSelection.length) {
      ElMessage.error('请选择项');
      return;
    }
    params = state.multipleSelection.map(i => i.orderId);
  }

  ElMessageBox.confirm(
      '确定出库吗？',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    const res = await axiosInstance.put('/orders/checkOut', {
      ids: params
    });

    if (res.resultCode === 200) {
      ElMessage.success("出库成功");
      getOrderList()
    } else {
      ElMessage.error("出库失败")
    }

  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消出库',
    })
  })
}

// 关闭订单方法
const handleClose = (id) => {
  let params;
  if (id) {
    params = [id]
  } else {
    if (!state.multipleSelection.length) {
      ElMessage.error('请选择项');
      return;
    }
    params = state.multipleSelection.map(i => i.orderId);
  }

  ElMessageBox.confirm(
      '确定关闭订单吗？',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      }
  ).then(async () => {
    const res = await axiosInstance.put('/orders/close', {
      ids: params
    });

    if (res.resultCode === 200) {
      ElMessage.success("关闭成功");
      getOrderList()
    } else {
      ElMessage.error("关闭失败")
    }

  }).catch(() => {
    ElMessage({
      type: 'info',
      message: '取消关闭',
    })
  })
}
</script>

<template>
    <el-row>
        <el-col :span="6">
            <el-input style="width:440px" @clear="searchUser" clearable v-model="searchForm.name" placeholder="请输入用户姓名"
                class="input-with-select">
                <template #append>
                    <el-button icon="Search" @click="searchUser">搜索</el-button>
                </template>
            </el-input>
        </el-col>
        <el-col :span="6" :offset="2">
            <el-button type="primary" @click="addUser()">增加用户</el-button>
        </el-col>
    </el-row>
    <el-row>
        <el-col :span="24">
            <el-card>
                <el-table :data="tableData" border style="width: 100%;margin-top:20px">
                    <el-table-column prop="id" label="ID" width="180" />
                    <el-table-column prop="username" label="姓名" width="180" />
                    <el-table-column prop="age" label="年龄" width="180" />
                    <el-table-column prop="phoneNumber" label="电话号码" width="180" />
                    <el-table-column prop="address" label="地址" width="180" />
                    <el-table-column label="操作" width="330">
                        <template #default="scope">
                            <el-button type="danger" size="small" @click="deleteUser(scope.row.id)">删除</el-button>
                            <el-button size="small"
                                @click="() => router.push({ path: '/user/detail', query: { id: scope.row.id } })">编辑</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!-- 分页 -->
                <el-pagination style="margin-top:20px" :current-page="searchForm.current" :page-size="searchForm.size"
                    :page-sizes="[10, 20, 30, 40]" layout="->,total, sizes, prev, pager, next, jumper" :total="total"
                    @size-change="handleSizeChange" @current-change="handleCurrentChange" />
            </el-card>
        </el-col>
    </el-row>
</template>

<script setup>
import userApi from "../../../api/user.js";
import { onMounted, reactive, ref } from "vue";
import { ElMessage, ElMessageBox } from 'element-plus';
import { useRouter } from 'vue-router'
const router = useRouter();
// Dom 挂载之后
onMounted(() => {
    getUserList();
})
// 用户数据
let tableData = ref([]);
let total = ref(0);

// 搜索条件
const searchForm = reactive({
    current: 1,
    size: 5,
    name: ''
})

// 获取用户列表
const getUserList = async () => {
    const res = await userApi.getUserList(searchForm);
    console.log(res);
    tableData.value = res.data;
    total.value = res.total;
}

const handleSizeChange = (size) => {
    searchForm.size = size;
    getUserList();
}
const handleCurrentChange = (current) => {
    searchForm.current = current;
    getUserList();
}
const searchUser = () => {
    searchForm.current = 1;
    getUserList();
}
// 增加用户
const addUser = () => {
    router.push("/user/add");
}

// 删除用户
const deleteUser = (id) => {
    ElMessageBox.confirm(
        '确定要删除该用户信息吗?',
        {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning',
        }
    ).then(async () => {
        const res = await userApi.delUser({ id: id });
        if (res.success) {
            ElMessage.success("删除成功")
            getUserList();
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

<style scoped>
.el-row {
    margin-bottom: 20px;
}

.el-row:last-child {
    margin-bottom: 0;
}

.el-col {
    border-radius: 4px;
}
</style>

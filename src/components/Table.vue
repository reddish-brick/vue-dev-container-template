<template>
    <div>
        <el-table :data="state.users">
            <el-table-column prop="id" label="Id"></el-table-column>
            <el-table-column prop="name" label="Name"></el-table-column>
            <el-table-column prop="age" label="Age"></el-table-column>
            <el-table-column prop="gender" label="Gender"></el-table-column>
            <el-table-column prop="address" label="Address"></el-table-column>
            <el-table-column prop="email" label="Email"></el-table-column>
            <el-table-column>
                <template #default="{ row }">
                    <el-button type="primary" size="small" @click="addData(row)">Add</el-button>
                    <el-button type="primary" size="small" @click="updateById(row)">Edit</el-button>
                    <el-button type="danger" size="small" @click="deleteById(row)">Delete</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>
  
<script setup>
import { useTableStore } from '../store/table.js'
import { onMounted, reactive, ref } from 'vue'

const state = reactive({
    users: []
});

const tableStore = useTableStore();

const fetchTableData = () => {
    state.users = tableStore.tableData;
}

const addData = (row) => {
    tableStore.addData(row);
}

const deleteById = (row) => {
    tableStore.deleteById(row.id);
}

const updateById = (row) => {
    const data = {
        id: row.id,
        name: "John Doe",
        age: 23,
        gender: "女",
        address: "上海",
        email: "john@example.com",
    }
    tableStore.updateById(row.id, data);
}

onMounted(async () => {
    fetchTableData();
})

</script>
  
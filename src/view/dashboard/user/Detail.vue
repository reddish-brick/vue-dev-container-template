<template>
    <div>
        <el-card class="box-card">
            <template #header>
                <div class="card-header">
                    <span>{{ route.meta.title }}</span>
                    <el-button size="mini" style="float:right" @click="router.go(-1)">返回</el-button>
                </div>
            </template>
            <el-form :model="form" ref="ruleFormRef" :rules="rules">
                <el-form-item label="姓名:" prop="username">
                    <el-input v-model="form.username"></el-input>
                </el-form-item>
                <el-form-item label="年龄:" prop="age">
                    <el-input v-model.number="form.age"></el-input>
                </el-form-item>
                <el-form-item label="电话号码:" prop="phoneNumber">
                    <el-input v-model="form.phoneNumber"></el-input>
                </el-form-item>
                <el-form-item label="地址:" prop="address">
                    <el-input v-model="form.address"></el-input>
                </el-form-item>
            </el-form>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="onSubmit()">{{ route.query.id ? "修 改" : "增 加" }}</el-button>
            </span>
        </el-card>
    </div>
</template>

<script setup>
import { onBeforeMount, reactive, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import userApi from "../../../api/user.js";

const route = useRoute();
const router = useRouter();
const form = reactive({
    id: '',
    username: '',
    password: '',
    age: '',
    phoneNumber: '',
    address: ''
})

const id = ref('');

onBeforeMount(async () => {
    id.value = route.query.id;
    if (id.value !== undefined) {
        const res = await userApi.getUserDetail({ id: id.value })
        Object.assign(form, res.data);
    }
})

const ruleFormRef = ref();
const rules = reactive({
    username: [
        { required: true, message: '请输入姓名', trigger: 'blur' },
        { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' },
    ],
    age: [
        { required: true, message: '请输入年龄', trigger: 'blur' },
        { type: 'number', message: '年龄必须是数字', trigger: 'blur' }
    ],
    phoneNumber: [
        { required: true, message: '电话号码不能为空' },
        {
            pattern: /^1[3-9]\d{9}$/,
            message: '电话号码格式不正确',
            trigger: 'blur',
        },
    ]
})

const onSubmit = async () => {
    if (!ruleFormRef) return;
    ruleFormRef.value.validate(async (valid) => {
        if (valid) {
            const formData = {
                id: id.value,
                username: form.username,
                password: form.password,
                age: form.age,
                address: form.address,
                phoneNumber: form.phoneNumber
            }

            let res = null;
            if (id.value != undefined) {
                res = await userApi.updateUserDetailById(formData);
            } else {
                res = await userApi.saveUser(formData);
            }


            if (res) {
                if (res.success) {
                    router.push("/user/list");
                } else {
                    ElMessage.error(res.message);
                }
            } else {
                ElMessage.error("服务器内部错误");
            }
        } else {
            return false;
        }
    });
};

</script>

<style  scoped>
.el-input {
    width: 50%;
}
</style>

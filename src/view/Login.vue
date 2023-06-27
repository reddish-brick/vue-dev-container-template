<template>
  <div>
    <el-container>
      <el-header>
        <div style="width:400px">
          <p style="float: left;">
            <el-icon color="#409EFF" :size="50">
              <ElementPlus/>
            </el-icon>
          </p>
          <p style="float: left;font-size: 25px; font-weight: bold">
            欢迎来到红砖后台管理系统
          </p>
        </div>

      </el-header>
      <el-main>
        <el-card class="login_card">
          <el-form :model="form" :rules="rules" ref="ruleFormRef" label-width="80px">
            <el-form-item label="账号：" prop="username">
              <el-input v-model="form.username" placeholder="请输入账号"/>
            </el-form-item>
            <el-form-item label="密码：" prop="password">
              <el-input type="password" placeholder="请输入密码" v-model="form.password"/>
            </el-form-item>
            <el-form-item>
              <div style="color: #333">登录表示您已同意<a>《服务条款》</a></div>
              <el-button type="primary" @click="onSubmit()">登录</el-button>
              <el-button type="primary" @click="resetForm()">重置</el-button>
              <el-checkbox v-model="form.checked" @change="!form.checked">下次自动登录</el-checkbox>
            </el-form-item>
          </el-form>
        </el-card>
      </el-main>
      <el-footer>
        <p>关于我们 | 联系我们 | 人才招聘 | 广告服务 | 友情链接</p>
        <p>Copyright © 2001-2022
          <el-tag>公众号：红砖</el-tag>
        </p>
      </el-footer>
    </el-container>
  </div>
</template>

<script setup>
import {reactive, ref} from "vue";
import {ElMessage} from "element-plus";
import router from "../router/index";
import md5 from 'js-md5'
import {useAdminUserStore} from "../store/login";

const form = reactive({
  username: "admin",
  password: "123456",
  checked: true
});

const adminUserStore = useAdminUserStore();

const ruleFormRef = ref();

const rules = reactive({
  username: [{required: true, message: "账号不能为空", trigger: "blur"}],
  password: [{required: true, message: "密码不能为空", trigger: "blur"}],
});

const onSubmit = async () => {
  if (!ruleFormRef) return;
  ruleFormRef.value.validate(async (valid) => {
    if (valid) {
      const res = await adminUserStore.login({
        userName: form.username || '',
        passwordMd5: md5(form.password)
      });
      console.log("res is: " + res)
      if (res) {
        if (res.resultCode === 200) {
          console.log(res.data)
          const adminUserInfo = splitAdminUserInfo(res.data);
          let adminUserId = adminUserInfo[1];
          let adminUserName = adminUserInfo[2];
          let adminPassword = adminUserInfo[3];
          adminUserStore.storeLoginInfoToLocal(adminUserId, adminUserName, adminPassword);
          adminUserStore.adminLoginUser.userId = adminUserId;
          adminUserStore.adminLoginUser.username = adminUserName;
          adminUserStore.adminLoginUser.pasword = adminPassword;
          router.push("/home");
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

const splitAdminUserInfo = (data) => {
  return data.split(" ");
}

const resetForm = () => {
  if (!ruleFormRef) return;
  ruleFormRef.value.resetFields();
};
</script>

<style scoped>
.el-container {
  height: 800px;
}

.el-header {
  height: 10%;
}

.el-main {
  height: 80%;
  background-image: url("../assets/img/bg.jpg");
  background-repeat: no-repeat;
  background-size: 100% 120%;
  background-position-y: bottom;
}

.login_card {
  margin: 100px 200px;
  width: 20%;
  min-width: 300px;
  height: 250px;
  border-radius: 10px;
}

.el-footer {
  height: 10%;
  text-align: center;
}
</style>

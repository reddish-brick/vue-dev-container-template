<template>
    <div>
        <el-container class="home-container">
            <!-- header -->
            <el-header>
                <el-row>
                    <el-col :span="4">
                        <p class="system-name">红砖的网站</p>
                    </el-col>
                    <el-col :offset="12" :span="8" style="min-width: 150px">
                        <el-dropdown style="float: right; margin: 20px 10px">
                            <span class="el-dropdown-link" style="color: #fff; cursor: pointer">
                                {{ username }} &nbsp;&nbsp; <el-icon class="el-icon--right">
                                    <arrow-down />
                                </el-icon>
                            </span>
                            <template #dropdown>
                                <el-dropdown-menu>
                                    <el-dropdown-item @click.native="logout">退出系统</el-dropdown-item>
                                </el-dropdown-menu>
                            </template>
                        </el-dropdown>
                        <el-avatar shape="square" :src="avatar" style="margin: 10px; float: right"></el-avatar>
                    </el-col>
                </el-row>
            </el-header>

            <el-container style="overflow: auto">
                <!-- 菜单 -->
                <el-aside>
                    <div class="toggle-button" @click="isCollapse = !isCollapse">
                        <el-icon :size="20">
                            <Expand v-if="isCollapse" />
                            <Fold v-if="!isCollapse" />
                        </el-icon>
                    </div>
                    <el-menu router :default-active="activePath" class="el-menu-vertical-demo" :collapse="isCollapse">
                        <el-sub-menu index="1">
                            <template #title>
                                <span>首页</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="/welcome" @click="saveActiveNav('/welcome')">
                                    <el-icon>
                                        <house />
                                    </el-icon>
                                    <span>Welcome</span>
                                </el-menu-item>
                                <el-menu-item index="/user/list" @click="saveActiveNav('/user/list')">
                                    <el-icon>
                                        <user />
                                    </el-icon>
                                    <span>用户管理</span>
                                </el-menu-item>
                            </el-menu-item-group>
                        </el-sub-menu>

                        <el-sub-menu index="2">
                            <template #title>
                                <span>首页配置</span>
                            </template>
                            <el-menu-item-group>
                                <el-menu-item index="/swiper">
                                    <el-icon>
                                        <Picture />
                                    </el-icon>
                                    轮播图配置
                                </el-menu-item>
                                <el-menu-item index="/new"><el-icon>
                                        <Sell />
                                    </el-icon>新品上线配置</el-menu-item>
                                <!--                            <el-menu-item index="/hot"><el-icon><StarFilled /></el-icon>热销商品配置</el-menu-item>-->
                                <!--                            <el-menu-item index="/recommend"><el-icon><ShoppingCart /></el-icon>为你推荐配置</el-menu-item>-->
                            </el-menu-item-group>
                        </el-sub-menu>

                        <el-sub-menu index="2">
                          <template #title>
                            <span>模块管理</span>
                          </template>
                          <el-menu-item-group>
                            <el-menu-item index="/category">
                              <el-icon>
                                <Menu/>
                              </el-icon>
                              分类管理
                            </el-menu-item>
                            <el-menu-item index="/good">
                              <el-icon>
                                <Goods/>
                              </el-icon>
                              商品管理
                            </el-menu-item>
                            <el-menu-item index="/member">
                              <el-icon>
                                <Member/>
                              </el-icon>
                              会员管理
                            </el-menu-item>
                            <el-menu-item index="/order">
                              <el-icon>
                                <List/>
                              </el-icon>
                              订单管理
                            </el-menu-item>
                          </el-menu-item-group>
                        </el-sub-menu>

                    </el-menu>
                </el-aside>
                <el-container>
                    <el-main>
                        <!-- 面包屑 -->
                        <!-- <Breadcrumb /> -->
                        <!-- 主要内容 -->
                        <router-view></router-view>
                    </el-main>
                    <el-footer>Copyright © 2022 红砖</el-footer>
                </el-container>
            </el-container>
        </el-container>
    </div>
</template>
<script setup>
import {onBeforeMount, ref} from 'vue';
import avatar from "../assets/img/avator.jpg"
import {useRouter} from 'vue-router'

const router = useRouter();

const username = ref(sessionStorage.getItem("username"));

// 挂载 DOM 之前
onBeforeMount(() => {
    activePath.value = sessionStorage.getItem("activePath")
        ? sessionStorage.getItem("activePath")
        : "/welcome"
})

let isCollapse = ref(false);
let activePath = ref("");
// 保存链接的激活状态
const saveActiveNav = (path) => {
    sessionStorage.setItem("activePath", path);
    activePath.value = path;
}
const logout = () => {
    // 清除缓存
    sessionStorage.clear();
    router.push("/login");
}
</script>

<style scoped>
.home-container {
    position: absolute;
    height: 100%;
    top: 0px;
    left: 0px;
    width: 100%;
    background: #f2f3f5;
}

.el-header {
    background: #2661ef;
    padding: 0 10px;
    overflow: hidden;
}

.system-name {
    color: #fff;
    font-size: 18px;
}

.el-aside {
    background: white;
    width: auto !important;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}

.el-footer {
    color: #cccccc;
    text-align: center;
    line-height: 60px;
}

.el-footer:hover {
    color: #2661ef;
}

.toggle-button {
    background-color: #d9e0e7;
    font-size: 18px;
    line-height: 24px;
    color: #fff;
    text-align: center;
    letter-spacing: 0.2em;
    cursor: pointer;
    color: black;
}

.el-menu-vertical-demo:not(.el-menu--collapse) {
    width: 200px;
    min-height: 400px;
}

.el-menu-item.is-active {
    color: #fff !important;
    font-size: 15px;
    font-weight: bold;
    background-color: #2661ef !important;
    border-radius: 2px;
    height: 50px;
    line-height: 50px;
    box-sizing: border-box;
    margin: 2px 5px 0px 2px;
}
</style>

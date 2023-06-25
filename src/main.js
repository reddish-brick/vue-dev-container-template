import {createApp} from 'vue';
import ElementPlus from 'element-plus';
import 'element-plus/theme-chalk/index.css';
import App from './App.vue';
import {createPinia} from 'pinia';
import router from './router/index.js';
import * as ElementPlusIconsVue from '@element-plus/icons-vue';

const orderStatus = {
    0: '待支付',
    1: '已支付',
    2: '配货完成',
    3: '出库成功',
    4: '交易成功',
    '-1': '手动关闭',
    '-2': '超时关闭',
    '-3': '商家关闭',
};

const app = createApp(App);

// 全局方法
app.config.globalProperties.$filters = {
    orderMap(status) {
        return orderStatus[status] || '未知状态';
    },
    prefix(url) {
        if (url && url.includes('/goods-img/')) {
            return url; //使用本地资源图片（public/goods-img/**）
        } else {
            // url = `http://backend-api-02.newbee.ltd${url}`;
            return url;
        }
    },
};

app.config.globalProperties.goTop = function () {
    const main = document.querySelector('.main');
    main.scrollTop = 0;
};

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component);
}

app.use(router);
app.use(ElementPlus);

const pinia = createPinia();
app.use(pinia);

app.mount('#app');

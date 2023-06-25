import {createApp} from "vue";
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import App from "./App.vue";
import {createPinia} from "pinia";
import router from "./router/index.js";
import * as ElementPlusIconsVue from '@element-plus/icons-vue'


const app = createApp(App);

for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}

app.use(router);
app.use(ElementPlus);

const pinia = createPinia();
app.use(pinia);

app.mount("#app");

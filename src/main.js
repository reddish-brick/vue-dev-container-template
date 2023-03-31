import { createApp } from "vue";
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import App from "./App.vue";
import { createPinia } from "pinia";
import router from "./router/index.js";

const app = createApp(App);
app.use(router);
app.use(ElementPlus);

const pinia = createPinia();
app.use(pinia);

app.mount("#app");

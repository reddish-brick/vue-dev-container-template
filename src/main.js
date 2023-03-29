import { createApp } from "vue";
import ElementPlus from "element-plus";
import "element-plus/theme-chalk/index.css";
import App from "./App.vue";
import { createPinia } from "pinia";

const app = createApp(App);

app.use(ElementPlus);

const pinia = createPinia();
app.use(pinia);

app.mount("#app");

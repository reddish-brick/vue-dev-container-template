import { defineConfig } from "vite";
import vue from "@vitejs/plugin-vue";
import AutoImport from "unplugin-auto-import/vite";
import Components from "unplugin-vue-components/vite";
import { ElementPlusResolver } from "unplugin-vue-components/resolvers";

export default defineConfig({
  build: {
    sourcemap: true,
  },
  server: {
    // open: false,
    port: 3000,
    proxy: {
      "/api": {
        target: "http://127.0.0.1:8080", // target 是后台接口地址
        changeOrigin: true,
        rewrite: (path) => path.replace(/^\/api/, ""), // api 就是 axios 实例的 baseURL 属性
      },
    },
  },
  plugins: [
    vue(),
    AutoImport({
      resolvers: [ElementPlusResolver()],
    }),
    Components({
      resolvers: [ElementPlusResolver()],
    }),
  ],
});

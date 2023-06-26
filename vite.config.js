import {defineConfig, loadEnv} from 'vite'
import Vue from '@vitejs/plugin-vue'
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import {ElementPlusResolver} from 'unplugin-vue-components/resolvers'

export default defineConfig(({mode, command}) => {
    // 根据当前工作目录中的 `mode` 加载 .env 文件, 设置第三个参数为 '' 来加载所有环境变量，而不管是否有 `VITE_` 前缀。
    const env = loadEnv(mode, process.cwd(), '')

    return {
        build: {
            sourcemap: true
        },
        server: {
            // open: false,
            port: 3000,
            proxy: {
                '/api': {
                    target: env.VITE_API_DOMAIN + '/manage-api/v1', // target 是后台接口地址
                    changeOrigin: true,
                    rewrite: (path) => path.replace(/^\/api/, ''), // api 就是 axios 实例的 baseURL 属性
                },
            },
        },
        plugins: [
            Vue(),
            AutoImport({
                // Auto import functions from Vue, e.g. ref, reactive, toRef...
                // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
                imports: ['vue'],

                // Auto import functions from Element Plus, e.g. ElMessage, ElMessageBox... (with style)
                // 自动导入 Element Plus 相关函数，如：ElMessage, ElMessageBox... (带样式)
                resolvers: [
                    ElementPlusResolver(),

                    // Auto import icon components
                    // 自动导入图标组件
                    IconsResolver({
                        prefix: 'Icon',
                    }),
                ],
            }),

            Components({
                resolvers: [
                    // Auto register icon components
                    // 自动注册图标组件
                    IconsResolver({
                        enabledCollections: ['ep'],
                    }),
                    // Auto register Element Plus components
                    // 自动导入 Element Plus 组件
                    ElementPlusResolver(),
                ],
            }),

            Icons({
                autoInstall: true,
            }),
        ],
    }
});

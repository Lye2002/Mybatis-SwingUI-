import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import installElementPlus from './plugins/element'
import './assets/iconfont.css'
import './assets/css/global.css'
import axios from "axios";

axios.defaults.baseURL='http://127.0.0.1:8081'
axios.interceptors.request.use(req=>{
    req.headers.Authorization=window.sessionStorage.getItem('token')
    return req
})

const app = createApp(App)
app.config.globalProperties.$http=axios


app.config.$BASE_URL ='http://127.0.0.1:8081'



installElementPlus(app)
app.use(router).mount('#app')

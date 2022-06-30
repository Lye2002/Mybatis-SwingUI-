import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Login from '../views/Login'
import Welcome from '../components/Welcome'
import User from '../components/user/User'
import Avatar from '@/views/Avatar'
import Article from "@/views/Article"
import Config from "@/views/Config"
import Reg from '@/views/Reg'
import Shop from '@/components/shop/Shop'
import Orders from '@/components/orders/orders'
const routes = [
  {
    path:'/',
    redirect:'/login'
  },
  {
    path: '/home',
    name: 'home',
    component: Home,
    redirect: '/welcome',
    children:[
      { path:'/welcome',name:'welcome',component:Welcome },
      { path: '/user',component: User},
      { path: '/avatar',component: Avatar},
      { path: '/article',component: Article},
      { path: '/config',component: Config},
      { path:'/shop',component:Shop},
      { path:'/orders',component:Orders}
    ]
  },
  {
    path: '/about',
    name: 'about',
    // route level code-splitting
    // this generates a separate chunk (about.[hash].js) for this route
    // which is lazy-loaded when the route is visited.
    component: () => import(/* webpackChunkName: "about" */ '../views/AboutView.vue')
  },
  {
    path:'/login',
    name:'login',
    component: Login
  },
  {
    path:'/reg',
    name:'reg',
    component: Reg
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

router.beforeEach((to,from,next)=>{
  if(to.path==='/login') return next()
  if(to.path==='/reg') return next()
  let tokenStr=window.sessionStorage.getItem('token')
  if(!tokenStr) return  next('/login')
  next()
})


export default router

import Vue from 'vue'
import Router from 'vue-router'
//导入登录页面组件
import Login from '@/views/authentication/login.vue'
import Register from "@/views/authentication/register"
import LoginStaff from '@/views/authentication/loginStaff.vue'
import Dashboard from '@/views/dashboard/dashboard.vue'
import Homepage from "../views/homepage/homepage.vue";


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/homepage',
      component: Dashboard
    },
    {
      // home页面并不需要被访问，只是作为其它组件的父组件
      path: '/dashboard',
      name: 'Dashboard',
      component: Dashboard,
      meta: {
        requireAuth: true
      },
      redirect: '/dashboard/index',
      children:[
        {
          path:'/dashboard/index',
          name:'Index',
          component:() => import('@/views/dashboard/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/dashboard/user',
          name:'User',
          component:()=>import('@/views/dashboard/user'),
          meta:{
            requireAuth:true
          }
        },
      ]
    },
    {
      path:'/login',
      name: 'Login',
      component: Login
    },
    {
      path:'/register',
      name:'Register',
      component: Register
    },
    {
      path:'/loginstaff',
      name: 'LoginStaff',
      component: LoginStaff
    },
    {
      path: '/homepage',
      name: 'Homepage',
      component: Homepage,
      redirect: '/products',
      children:[
        {
          path:'/home',
          name:'Home',
          component:() => import('@/views/homepage/home'),
        },
        {
          path:'/products',
          name:'Products',
          component:() => import('@/views/homepage/products'),
        },
        {
          path:'/about',
          name:'About',
          component:() => import('@/views/homepage/about'),
        },
        {
          path: '/products/:productId',
          name: 'productDetail',
          component: () => import('@/views/homepage/productDetail.vue'),
        },
      ]
    },
  ]
})

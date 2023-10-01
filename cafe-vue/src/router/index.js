import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
//导入登录页面组件
import Login from '@/views/login.vue'
import Dashboard from '@/views/dashboard.vue'
import Register from "@/views/registerNew"
import Products from "@/views/products"


Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/home',
      component: Dashboard
    },
    {
      // home页面并不需要被访问，只是作为其它组件的父组件
      path: '/home',
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
     // {
     //   path:'/Register',
     //     name:'Register',
     //   component: Register
     // },
    {
      path:'/registerNew',
      name:'RegisterNew',
      component: Register
    },
    {
      path:'/products',
      name:'Products',
      component: Products
    }
  ]
})

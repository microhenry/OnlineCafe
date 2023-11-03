import Vue from 'vue'
import Router from 'vue-router'
// Import views
import Login from '@/views/authentication/login.vue'
import Register from "@/views/authentication/register"
import LoginStaff from '@/views/authentication/loginStaff.vue'
import Homepage from "../views/homepage/homepage.vue";


Vue.use(Router)

export default new Router({
  routes: [
    {
      // Default router
      path: '/',
      name: 'Default',
      redirect: '/homepage',
    },
    {
      // Dashboard router
      path: '/dashboard',
      name: 'Dashboard',
      component:() => import('@/views/dashboard/dashboard'),
      meta: {
        requireStaffAuth: true
      },
      redirect: '/dashboard/user',
      children:[
        {
          path:'/dashboard/index',
          name:'Index',
          component:() => import('@/views/dashboard/index'),
          meta:{
            requireStaffAuth:true
          }
        },
        {
          path:'/dashboard/user',
          name:'User',
          component:()=>import('@/views/dashboard/user'),
          meta:{
            requireStaffAuth:true
          }
        },
        {
          path:'/dashboard/product',
          name:'Product',
          component:()=>import('@/views/dashboard/product'),
          meta:{
            requireStaffAuth:true
          }
        },
        {
          path:'/dashboard/order',
          name:'Order',
          component:()=>import('@/views/dashboard/order'),
          meta:{
            requireStaffAuth:true
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
      redirect: '/home',
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
        {
          path: '/userpanel',
          name: 'userPanel',
          component: () => import('@/views/homepage/userpanel/userpanel.vue'),
          redirect: '/userpanel/profile',
          meta:{
            requireUserAuth:true
          },
          children:[
            {
              path: '/userpanel/profile',
              name: 'profile',
              component: () => import('@/views/homepage/userpanel/profile.vue'),
              meta:{
                requireUserAuth:true
              },
            },
            {
              path: '/userpanel/cart',
              name: 'cart',
              component: () => import('@/views/homepage/userpanel/cart.vue'),
              meta:{
                requireUserAuth:true
              },
            },
            {
              path: '/userpanel/order',
              name: 'order',
              component: () => import('@/views/homepage/userpanel/order.vue'),
              meta:{
                requireUserAuth:true
              },
            },
          ]
        },
      ]
    },
    {
      path: '/:catchAll(.*)',
      name: 'not-found',
      component: () => import('@/components/NotFound.vue')
    }
  ]
})

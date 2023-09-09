import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
//导入登录页面组件
import Login from '@/views/login.vue'
import Home from '@/views/home.vue'
import Register from "@/views/Register"
// 菜单
import menu from '../views/menu/menu.vue'//父组件 作为其子路由渲染出口使用
import menuIndex from '../views/menu/menuIndex.vue'//真正的menu的默认视图
import beverages from '@/views/menu/beverages.vue'
import food from '@/views/menu/food.vue'
import coffee from '@/views/menu/coffee.vue'
import merchandise from '@/views/menu/merchandise.vue'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Default',
      redirect: '/home',
      component: Home
    },
    {
      // home页面并不需要被访问，只是作为其它组件的父组件
      path: '/home',
      name: 'Home',
      component: Home,
      meta: {
        requireAuth: true
      },
      redirect: '/index',
      children:[
        {
          path:'/index',
          name:'Index',
          component:() => import('@/views/home/index'),
          meta:{
            requireAuth:true
          }
        },
        {
          path:'/user',
          name:'User',
          component:()=>import('@/views/user/index'),
          meta:{
            requireAuth:true
          }
        },
      ]
    }, // 其他的路由都是 / 的子孙路由 这样点其他路由地址页面就不会消失 但是因此DOM树也比较庞大，
        {
          path: '/menu',
          name: 'menu',
          // 由于每个 / 的子路由都有后代路由，所以每个相对而言的父路由所在的组件 仅作为子路由的出口来使用
          component: menu,
          children: [
            {
              // 因此需要多一个视图 用来渲染父路由的默认视图，可以减少一环 v-show 的判断；到@/components/indexleft.vue 文件中还有解释说明
              path: '/menu',
              name: 'menuIndex',
              component: menuIndex
            },
            { // 官网中 menu 的层级最多 ’/menu/儿子/孙子‘ 我这里只写到 /menu/儿子
              // 在官网的孙子页面中，他们只是做一个列表筛选后的显示而已 ，后续利用一个计算属性渲染动态的数据，渲染显示即可，不需要再写页面，太冗长！
              path: 'beverages',
              name: 'beverages',
              component: beverages,
            }, {
              path: 'food',
              name: 'food',
              component: food,
            }, {
              path: 'coffee',
              name: 'coffee',
              component: coffee,
            }, {
              path: 'merchandise',
              name: 'merchandise',
              component: merchandise,
            },
          ]
    },
    {
      path:'/login',
      name: 'Login',
      component: Login
    },
     {
       path:'/Register',
         name:'Register',
       component:Register
     },

  ]
})

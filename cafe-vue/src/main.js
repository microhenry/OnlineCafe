// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
//import ElementUI
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import locale from 'element-ui/lib/locale/lang/en'
//add axios
var axios= require('axios')
Vue.prototype.$axios = axios
// send request to http://localhost:8088/api
axios.defaults.baseURL = 'http://localhost:8088/api'
//import store
import store from './store'



Vue.config.productionTip = false

/* eslint-disable no-new */

Vue.use(ElementUI, { locale })

//hook
router.beforeEach((to, from, next) => {
    if (to.meta.requireStaffAuth) {
      //If the router require staff authentication
      if (store.state.token) {
        //If store has the token
        //TODO: Find better validation methods.
        if(store.state.isStaff === false){
          Vue.prototype.$message.error("Error: Please login to your staff account.");
          next({
            path: 'loginstaff',
            query: { redirect: to.fullPath }
          })
        } else {
          next()
        }
      } else {
        //If store has no token, redirect to the login page.
        Vue.prototype.$message.error("Error: Please login to your staff account.");
        next({
          path: 'loginstaff',
          query: { redirect: to.fullPath }
        })
      }
    } else if(to.meta.requireUserAuth){
      //If the router require user authentication
      if (store.state.token && store.state.isStaff === false) {
        //If store has the token
        next()
      } else {
        //If store has no token, redirect to the login page.
        Vue.prototype.$message.error("Error: Please login to your customer account.");
        next({
          path: 'login',
          query: { redirect: to.fullPath }
        })
      }
    } else {
      next()
    }
  }
)


new Vue({
  el: '#app',
  router,
  store,
  components: { App },
  template: '<App/>'
})


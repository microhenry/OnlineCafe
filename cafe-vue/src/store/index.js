import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem("token"),
    user: JSON.parse(sessionStorage.getItem("user"))
  },
  mutations: {
    // set
    SET_TOKENN: (state, token) => {
      state.token = token
      sessionStorage.setItem("token", token)
      console.log("SET_TOKENN: "+state.token)
    },
    SET_USER: (state, user) => {
      state.user = user
      state.isStaff = false;
      sessionStorage.setItem("user", JSON.stringify(user))
      sessionStorage.setItem("isStaff", JSON.stringify(false))
      console.log("SET_USER: "+state.user)
    },
    SET_STAFF: (state, user) => {
      state.user = user;
      state.isStaff = true
      sessionStorage.setItem("user", JSON.stringify(user))
      sessionStorage.setItem("isStaff", JSON.stringify(true))
      // console.log("SET_STAFF: "+state.staff)
    },
    REMOVE_INFO : (state) => {
      state.token = ''
      state.user = {}
      state.isStaff = false
      sessionStorage.setItem("token", '')
      sessionStorage.setItem("user", JSON.stringify(''))
      sessionStorage.setItem("isStaff", JSON.stringify(false))
    },
  },
  getters: {
    getUser(state) {
      return state.user;
    }
  },
  actions: {
  },
  modules: {
  }
})

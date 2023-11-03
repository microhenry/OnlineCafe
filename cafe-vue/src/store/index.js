import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    token: sessionStorage.getItem("token"),
    user: JSON.parse(sessionStorage.getItem("user")),
    isStaff: JSON.parse(sessionStorage.getItem("isStaff")),
    cartNum: 0,
  },
  mutations: {
    // set
    SET_TOKENN: (state, token) => {
      state.token = token
      sessionStorage.setItem("token", token)
    },
    SET_USER: (state, user) => {
      state.user = user
      state.isStaff = false;
      sessionStorage.setItem("user", JSON.stringify(user))
      sessionStorage.setItem("isStaff", JSON.stringify(false))
    },
    SET_STAFF: (state, user) => {
      state.user = user;
      state.isStaff = true
      sessionStorage.setItem("user", JSON.stringify(user))
      sessionStorage.setItem("isStaff", JSON.stringify(true))
    },
    REMOVE_INFO : (state) => {
      state.token = ''
      state.user = {}
      state.isStaff = false
      sessionStorage.setItem("token", '')
      sessionStorage.setItem("user", JSON.stringify(''))
      sessionStorage.setItem("isStaff", JSON.stringify(false))
    },
    updateCartNum(state, cartNum) {
      state.cartNum = cartNum;
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

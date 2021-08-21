<template>
  <!--登录-->
  <div class="wrap">
    <div class="tdbModule loginPage">
      <div class="registerTitle">用户登录</div>
      <div class="registerCont">
        <ul>
          <li>
            <span class="dis"></span>
            <input v-model="userIf.userType" type="radio" value="1" />
            投资人
            <input v-model="userIf.userType" type="radio" value="2" />
            借款人
          </li>
          <li>
            <span class="dis">手机号：</span>
            <input class="input" v-model="userIf.mobile" />
          </li>

          <li>
            <span class="dis">密码：</span>
            <input class="input" v-model="userIf.password" type="password" />
          </li>
          <li class="btn">
            <button @click="login()" :class="{ disabled: !isValid }">
              立即登录
            </button>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import '~/assets/css/register.css'
import cookie from 'js-cookie'

export default {
  data() {
    return {
      userIf:{
        userType: 1,
      },
      isValid: true, //表单校验是否成功
    }
  },

  methods: {
    //登录
    login() {
      this.$axios.$post('/api/core/userInfo/login',this.userIf).then(resp=>{
        cookie.set('userInfo',resp.data.userinfo)
        window.location.href='/user'
      })
    },
  },
}
</script>

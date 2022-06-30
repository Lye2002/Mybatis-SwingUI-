<template>
<div class="reg"><h3 style="text-align: center;margin-bottom: 30px">用户注册</h3>
  <el-form ref="loginRef" :model="loginForm" :rules="loginRules" label-width="60px">
    <el-form-item label="账号" prop="username">
      <el-input v-model="loginForm.username" />
    </el-form-item>
    <el-form-item label="密码" prop="password">
      <el-input v-model="loginForm.password" type="password" />
    </el-form-item>
    <el-form-item class="btn">
      <el-button type="warning" size="small" @click="reg()">注册</el-button>
      <el-button type="success" size="small" @click="resetForm()">重置</el-button>
    </el-form-item>
  </el-form>
</div>
</template>

<script>
export default {
name: "Reg",
  data(){
    return{
      loginForm:{
        username:'admin',
        password:'admin'
      },
      loginRules:{
        username:[{required:true,message:'请输入用户名',trigger:'blur'},{},{}],
        password:[{required:true,message:'请输入密码',trigger:'blur'}]
      }
    }
  },
  methods:{
    reg(){
      this.$refs.loginRef.validate(async v=>{
        // console.log(v)
        if(!v) return
        const {data:res}=await this.$http.post('/users/reg',this.loginForm)
        console.log(res)

      })
    },
    resetForm(){
      // console.log(this)
      this.$refs.loginRef.resetFields()
    }

  }
}
</script>

<style scoped>
.reg{width: 280px;height: 220px;margin: 50px auto;padding: 20px;border: 1px solid #ccc;border-radius: 10px;
  box-shadow: 0 0 1px rgba(0,0,0,0.2)}
.btn{
  float: right;}
</style>
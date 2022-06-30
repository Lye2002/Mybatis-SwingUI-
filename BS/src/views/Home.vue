<template>
  <div class="layout">
    <el-container>
      <el-header>
        <div>
          <img src="../assets/logo.png" alt="">
          <h3>商品后台管理系统</h3>
        </div>
       <div>  
          <el-avatar :size="size" :src="avatarUrl"></el-avatar>
         <p>{{user}}</p><el-button type="info" @click="logout()">退出</el-button></div>
      </el-header>
      <el-container>
        <el-aside width="200px">
          <el-menu
              active-text-color="#ffd04b"
              background-color="#222222"
              class="el-menu-vertical-demo"
              text-color="#fff"
              router
              :default-active="activePath"
          >
            <el-menu-item :index="item.path" @click="saveMenuStatus(item.path)" v-for="item in menulist" :key="item.id">
<!--              <el-icon><Menu /></el-icon>-->
              <!-- <i class="el-icon-setting"></i> -->
             
                   <template #title>
                            <el-icon><component :is="item.icon"></component></el-icon>
                        </template>
                 
              <!-- <i :class="item.icons"></i> -->
              <span>{{ item.title }}</span>
            </el-menu-item>
<!--            <el-sub-menu index="1">
              <template #title>
                <el-icon><Location /></el-icon>
                <span>用户管理</span>
              </template>
              <el-menu-item-group>
                <el-menu-item index="/user"> <el-icon><Menu /></el-icon>信息管理</el-menu-item>
                <el-menu-item index="1-2"> <el-icon><Menu /></el-icon>头像管理</el-menu-item>
              </el-menu-item-group>
            </el-sub-menu>
           <el-menu-item index="/avatar">
              <el-icon><Menu /></el-icon>
              <span>头像管理</span>
            </el-menu-item>
            <el-menu-item index="/article" >
              <el-icon><Document /></el-icon>
              <span>文章管理</span>
            </el-menu-item>
            <el-menu-item index="/config">
              <el-icon><Setting /></el-icon>
              <span>系统配置</span>
            </el-menu-item>-->
          </el-menu>

        </el-aside>
        <el-main>
          <router-view></router-view>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
// @ is an alias to /src
 import { Location,Menu,Document,Setting,Delete } from '@element-plus/icons-vue'


export default {
  name: 'HomeView',
  data(){
    return{
      avatarUrl:'',
      menulist:[],
      activePath:'',
      user:''
    }
  },
/*  components:{
    Location,Menu,Document,Setting,Delete
  },*/
  created(){
    this.loadMenu()
    this.activePath=window.sessionStorage.getItem('activePath')
  },
  mounted(){
    //this.getTouxiang()
  },
  methods: {
    logout(){
      window.sessionStorage.clear ()
      this.$router.push ('/login')
    },
   async loadMenu(){
      const {data:res}=await this.$http.get('/menu')
      // console.log(res.userInfo.username)
      console.log(res)
       this.user= window.sessionStorage.getItem('username',res.username)
     // window.sessionStorage.setItem('username',res.userInfo.username)
     this.menulist=res
    },
    saveMenuStatus(activePath){
      window.sessionStorage.setItem('activePath',activePath)
    },
    async getTouxiang(){
      const {data}=await this.$http.get('/up/avator')
      console.log(data.imgurl)
      this.avatarUrl=data.imgurl
    }


  }
}
</script>
<style scoped>
.layout {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0
}

.el-container {
  background-color: #222222;
  height: 100%
}

.el-header {
  height: 80px;
  display: flex;
  align-items: center;
  color: #fff;
  justify-content: space-between
}

.el-header div {
  display: flex;
  align-items: center
}

.el-header img {
  width: 50px;
  height: 50px;
}

.el-main {
  background-color: #F7F7F7
}

.el-sub-menu .el-menu-item-group .el-menu-item{
  height: 32px;
}
.el-menu-item-group__title{
  padding: 0;
}
</style>

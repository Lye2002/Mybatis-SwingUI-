<template>
  <div>
    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
    </el-breadcrumb>
    <el-card class="box-card" style="box-shadow: 0 0 2px rgba(0,0,0,0.1)">
      <el-row :gutter="20">
        <el-col :span="6">
          <el-input clearable placeholder="请输入搜索内容" v-model="userInfo.query" @clear="getUserList()" @blur="getUserList">
            <template #append>
              <el-button :icon="Search"  @click="getUserList" />
              <!--<i class="iconfont icon-search"></i>-->
            </template>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible=true">添加用户</el-button>
        </el-col>
      </el-row>
      <el-table :data="users" border >
        <el-table-column type="index" label="序号" width="70"></el-table-column>
        <el-table-column prop="username" label="用户名" width="300" />
        <el-table-column prop="password" label="密码" width="380" />
        <el-table-column prop="firsttime" label="更新时间" width="300" />
        <el-table-column prop="money" label="账户余额" width="300" />
         <!-- <el-table-column prop="status" label="状态" >
          <template #default="scope"> -->
           <!-- <el-switch
                v-model.number="scope.row.status"
                :active-value=1
                :inactive-value=0
                @change="userStatusChange(scope.row)"
            /> -->
          <!-- {{scope.row}} 
         </template>
        </el-table-column>  -->
        <el-table-column label="操作">
          <template #default="scope">
            <el-button type="primary" :icon="Edit" @click="showEditDialog(scope.row.username)" circle />
            <el-button type="success" @click="removeUserById(scope.row.username)" :icon="Delete" circle />
          </template>
        </el-table-column>
      </el-table>




      <el-pagination
          v-model:currentPage="userInfo.pagenum"
          v-model:page-size="userInfo.pagesize"
          :page-sizes="[1, 2, 3, 5]"
          :small=true
          layout="total, sizes, prev, pager, next, jumper"
          :total="total"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
      />
    </el-card>



     <!-- 添加用户的对话框-->
    <el-dialog
        v-model="addDialogVisible"
        title="添加用户"
        width="30%"
        @close="addUserClose"
    >

      <el-form
          ref="addUserFormRef"
          :model="addUserForm"
          :rules="addUserRules"
          label-width="60px"
          class="demo-ruleForm"
          status-icon
      >
        <el-form-item label="账号" prop="username">
          <el-input v-model="addUserForm.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addUserForm.password" />
        </el-form-item>
        <el-form-item label="余额" prop="money">
          <el-input v-model="addUserForm.money" />
        </el-form-item>

      </el-form>

      <template #footer>
      <span class="dialog-footer">
        <el-button @click="addDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="addUser()">确认</el-button>
      </span>
      </template>
    </el-dialog>
     <!--修改用户的对话框 -->
    <el-dialog
        v-model="editDialogVisible"
        title="修改用户信息"
        width="30%"
        @close="editUserClose"
    >
      <el-form
          ref="editUserFormRef"
          :model="editUserForm"
          :rules="editUserRules"
          label-width="60px"
          status-icon
      >
        <el-form-item label="账号" prop="username">
          <el-input v-model="editUserForm.username" disabled  />
        </el-form-item>

        <el-form-item label="密码">
          <el-input v-model="editUserForm.password" />
        </el-form-item>
              <el-form-item label="余额">
          <el-input v-model="editUserForm.money" />
        </el-form-item>
      </el-form>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="editDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="editUser"
        >确定</el-button
        >
      </span>
      </template>
    </el-dialog>

  </div>
</template>

<script>
import {Search,Edit,Delete} from '@element-plus/icons-vue'

export default {
  name: "User",
  setup() {
    return {
      Search, Edit, Delete
    }
  },
  data() {
    let checkEmail = (rules, value, cb) => {
      const emailReg = /^[a-zA_Z0-9_-]+@([a-zA_Z0-9_-])+(\.[a-zA_Z0-9_-])+/
      if (emailReg.test(value)) {
        return cb()
      }
      cb(new Error('非法邮箱'))
    }
    let checkMobile = (rules, value, cb) => {
      const mobileReg = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
      if (mobileReg.test(value)) {
        return cb()
      }
      cb(new Error('非法手机号'))
    }
    return {
      userInfo: {
        query: '',
        pagenum: 1,
        pagesize: 3
      },
      users: [],
      total: 0,
      addDialogVisible: false,
      editDialogVisible:false, //修改用户对话框
      editForm: {}, //用户保存编辑前用户信息的对象
      addUserForm: {
        username: '',
        password: '',
        money: '',
      },
      editUserForm:{
        username: '',
        password: '',
        money:'',
      },
      // addUserRules: {
      //   username: [
      //     {required: true, message: '请输入用账号', trigger: 'blur'},
      //     //{min:3,max:6,message:"账号至少3位，最长6位",trigger:'blur'},
      //     {pattern: /^[a-zA_Z0-9]{3,10}$/, message: "账号至少3位，最长10位,由字符和数字组成", trigger: 'blur'}
      //   ],
      //   password: [
      //     {required: true, message: '请输入密码', trigger: 'blur'},
      //   ],
      //   email: [
      //     {required: true, message: '请输入邮箱', trigger: 'blur'},
      //     {validator: checkEmail, trigger: 'blur'}
      //   ],
      //   mobile: [
      //     {required: true, message: '请输入手机号', trigger: 'blur'},
      //     {
      //       pattern: /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
      //       message: '手机号码不合法',
      //       trigger: 'blur'
      //     }
      //   ]
      // },
      // editUserRules:{
      //   password: [
      //     {required: true, message: '请输入密码', trigger: 'blur'},
      //     {pattern: /^[a-zA_Z0-9]{3,10}$/, message: "账号至少3位，最长10位,由字符和数字组成", trigger: 'blur'}
      //   ],
      //   email: [
      //     {required: true, message: '请输入邮箱', trigger: 'blur'},
      //     {validator: checkEmail, trigger: 'blur'}
      //   ],
      //   mobile: [
      //     {required: true, message: '请输入手机号', trigger: 'blur'},
      //     {
      //       pattern: /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/,
      //       message: '手机号码不合法',
      //       trigger: 'blur'
      //     }
      //   ]
      // }
    }
  },
  created() {
    this.getUserList()
  },
  methods: {
    async getUserList() {
      let {data:res} = await this.$http.get('/users/getAllUser')
      console.log(await this.$http.get('/users/getAllUser', {params: this.userInfo}))
     console.log(res.data)
     this.users = res.data
      this.total = res.length
      console.log(this.users)
    },
    handleSizeChange(pagesize) {
      // console.log(pagesize)
      this.userInfo.pagesize = pagesize
      this.getUserList()
    },
    handleCurrentChange(pagenum) {
       console.log(pagenum)
      this.userInfo.pagenum = pagenum
      this.getUserList()
    },
    async userStatusChange(userinfo) {
      // console.log(status)
      const {data: res} = await this.$http.put(`/users/userStatus/${userinfo.id}/status/${userinfo.status}`)
      // console.log(res)
      if (res.flag === false) {
        this.userInfo.status = !this.userInfo.status
        return this.$message.error(res.msg)
      }
      this.$message.success(res.msg)
    },
    async removeUserById(id) {
      // console.log(id)
      const confirmRes = await this.$confirm(
          '永久删除用户，是否继续?',
          '提示',
          {
            confirmButtonText: '确认',
            cancelButtonText: '取消',
            type: 'warning',
          }).catch(err => {
              return err
      })
      // console.log(confirmRes)
      if (confirmRes !== 'confirm') {
        return this.$message.info('取消删除')
      }
      const {data: res} = await this.$http.delete('/users/' + id)
      if (res.flag === false) return this.$message.error("删除失败")
      this.$message.success("删除成功")
      await this.getUserList()
    },


    addUserClose() {
      this.$refs.addUserFormRef.resetFields()
    },
    async addUser(){
        console.log('______________')

        const {data}=await this.$http.post('/users',this.addUserForm)
        // console.log(data)
        if(data.flag===false) return this.$message.error("增加失败")
        this.$message.success("增加成功")
        this.addDialogVisible=false
        await this.getUserList()

    },
    async showEditDialog(id){
      console.log(id)
      this.editDialogVisible=true
       const {data} = await this.$http.get('/users/getUserById/'+id)
        // console.log(data)
       this.editUserForm=data.data
       console.log(this.editUserForm)
      // if (data.flag==false) return this.$message.error(data.msg)
      // this.editForm=data.data[0]
      // console.log(data.data[0])
    },
    editUserClose(){
      this.$refs.editUserFormRef.resetFields()
    },
   async editUser(){
      //this.$refs.editUserFormRef.validate(async v=>{
        // console.log(v)
        // if(!v) return
        const {data}=await this.$http.put('/users/editUser/',this.editUserForm)
      if(data.flag==false) return this.$message.error(data.msg)
        this.$message.success("修改成功")
        this.editDialogVisible=false
        await this.getUserList()
 //     })
    }
  }
}
</script>

<style scoped>
.el-pagination{
  margin-top:15px;
}
</style>

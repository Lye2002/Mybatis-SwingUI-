<template>
  <div class="hello">
    Hello <i class="el-icon-delete"></i>
    <Location style="width: 1em; height: 1em; margin-right: 8px;color:red" />
    <Menu style="width: 1em; height: 1em; margin-right: 8px" />
    <ul>
      <li v-for="i in icons">
        <i :class="i"></i>
      </li>
    </ul>

    <el-input
        placeholder="Please input"
        class="input-with-select"
    >
      <template #prepend>
        <el-button :icon="Search" />
      </template>
    </el-input>


    <el-form :model="form" label-width="120px">
      <el-form-item label="Activity name">
        <el-input v-model="form.name" :suffix-icon="Search" />
      </el-form-item>
      <el-form-item label="Activity name">
        <el-input v-model="form.name" :prefix-icon="Location" />
      </el-form-item>
    </el-form>


    <el-button type="text" @click="dialogVisible = true">点击打开 Dialog</el-button>

    <el-dialog
        v-model="addDialogVisible"
        title="Tips"
        width="30%"
    >
      <span>This is a message</span>
      <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">Cancel</el-button>
        <el-button type="primary" @click="dialogVisible = false"
        >Confirm</el-button
        >
      </span>
      </template>
    </el-dialog>
  </div>
</template>

<script>
import { Location,Menu,Document,Setting,Delete,Search } from '@element-plus/icons-vue'

export default {
  name: 'welcome',
  setup(){
    return{
      Search,Location
    }
  },
  data(){
    return{
      menulist:[],
      icons:[
          'iconfont icon-zuozhe','iconfont icon-gouwu'
      ],
      form:{
        name:"",
        region:"",
        date1:''
      }
    }
  },
  components:{
    Location,Menu,Document,Setting,Delete,Search
  },
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
h3 {
  margin: 40px 0 0;
}
ul {
  list-style-type: none;
  padding: 0;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #42b983;
}
</style>

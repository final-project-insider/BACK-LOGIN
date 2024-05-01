import { createApp } from 'vue'
import BaseSpinner from '@/components/UI/BaseSpinner'

// import router from '@/router'
import store from "@/store"
import App from './App.vue'

const app = createApp(App);

// app.use(router)
app.use(store)

app.component('base-spinner', BaseSpinner)

app.mount("#app")
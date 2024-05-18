import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap";
import { useKakao } from 'vue3-kakao-maps/@utils';
import vuetify from './plugins/vuetify'

useKakao('89efa28a9657cfa2579ad7d4c79cc3e8');
const app = createApp(App)
app.use(vuetify);
app.use(router);

app.mount("#app");

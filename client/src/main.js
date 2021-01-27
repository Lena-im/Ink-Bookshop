import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import "@/assets/css/normalize-and-reset.css";
import "@/assets/css/global.css";
import store from "./store";
import Vuelidate from "vuelidate";
import TreeView from "vue-json-tree-view";

Vue.config.productionTip = false;

const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2
});

Vue.filter("asDollarsAndCents", function(cents) {
  return PriceFormatter.format(cents / 100.0);
});

Vue.use(Vuelidate);
Vue.use(TreeView);

new Vue({
  router,
  store,
  render: function(h) {
    return h(App);
  }
}).$mount("#app");

Vue.config.devtools = true;

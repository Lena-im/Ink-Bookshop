<template>
  <div id="app">
    <app-header></app-header>
    <router-view id="router-view" :key="$route.fullPath"></router-view>
    <app-footer></app-footer>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
import { CART_STORAGE_KEY, ORDER_DETAIL_STORAGE_KEY } from "./store";

export default {
  name: "App",
  components: {
    AppHeader,
    AppFooter
  },
  created() {
    this.$store.dispatch("fetchCategories");
    const cartString = localStorage.getItem(CART_STORAGE_KEY);
    if (cartString) {
      const shoppingCart = JSON.parse(cartString);
      this.$store.commit("SET_CART", shoppingCart);
    }
    const orderDetails = localStorage.getItem(ORDER_DETAIL_STORAGE_KEY);
    if (orderDetails) {
      const order = JSON.parse(orderDetails);
      this.$store.commit("SET_ORDER_DETAILS", order);
    }
  }
};
</script>

<style>
body {
  font: 1em normal Verdana, Tahoma, sans-serif;
  color: black;
  font-size: 20px;
  margin: 20px;
}

#app {
  background-image: url("../src/assets/images/site/background.jpg");
  background-position: center center;
  background-repeat: no-repeat;
  background-size: cover;
  background-attachment: fixed;
  opacity: 0.9;
  height: 100%;
  display: flex;
  flex-direction: column;
}

a,
a:visited,
input[type="submit"] {
  color: gray;
}

a:hover,
a:active,
input[type="submit"]:hover,
ul.buttons a:hover {
  color: #d90429;
}
</style>

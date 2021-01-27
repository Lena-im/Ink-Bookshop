<template>
  <div>
    <section id="cartStatusSection">
      <div id="emptyCart" v-if="cart.empty">
        Your shopping cart is empty.
      </div>
      <div id="myCart" v-else>
        <p style="font-size: 35px; font-weight: bold">Your Cart</p>
        <br />
        <p style="font-size: 18px">Items: {{ cart.numberOfItems }}</p>
        <br />
        <p style="font-size: 18px">
          Subtotal: {{ cart.subtotal | asDollarsAndCents }}
        </p>
      </div>
      <div id="clearCart" v-if="!cart.empty">
        <button id="clear" @click="clearCart()">Clear Cart</button>
      </div>
    </section>

    <cart-table />

    <section id="cartNavigationSection">
      <router-link
        class="cartNavigationButton continueShopping"
        :to="{
          name: 'category',
          params: { name: $store.state.selectedCategoryName }
        }"
        >Continue Shopping</router-link
      >

      <br />
      <router-link
        class="cartNavigationButton checkOut"
        v-if="!cart.empty"
        :to="{ name: 'checkout' }"
        >Proceed to Checkout</router-link
      >
    </section>
  </div>
</template>

<script>
import { mapState } from "vuex";
import CartTable from "../components/CartTable";

export default {
  name: "Cart.vue",
  components: {
    CartTable
  },
  computed: {
    ...mapState(["cart"])
  },
  methods: {
    clearCart: function() {
      this.$store.dispatch("clearCart");
    },
    checkCategory: function() {
      if (
        this.$store.state.selectedCategoryName == null ||
        this.$store.state.selectedCategoryName == ""
      ) {
        this.$store.state.selectedCategoryName = "War";
      }
    }
  }
};
</script>

<style scoped>
#emptyCart {
  color: black;
  font-family: AppleGothic;
  font-size: 40px;
  padding: 50px 80px 20px 50px;
  margin-left: 150px;
}

#myCart {
  color: black;
  font-family: AppleGothic;
  padding: 50px 80px 30px 200px;
}

#clearCart {
  margin-left: 200px;
  margin-top: 20px;
  margin-bottom: 15px;
  display: inline-block;
}

#clear {
  font-size: 20px;
  font-family: AppleGothic;
  color: #1a1a1a;
  padding: 8px 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  background-color: #cccccc;
  border: none;
  outline: none;
}

#clear:hover {
  background-color: #999999;
}

#cartNavigationSection {
  padding: 30px 200px 50px 0px;
  margin-left: 200px;
  display: flex;
  justify-content: space-between;
}

.cartNavigationButton {
  font-size: 20px;
  font-family: AppleGothic;
  color: #1a1a1a;
  padding: 10px 25px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  background-color: #cccccc;
}

.cartNavigationButton:hover {
  background-color: #999999;
}
</style>

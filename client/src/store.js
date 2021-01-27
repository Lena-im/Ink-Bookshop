import Vue from "vue";
import Vuex from "vuex";
import ApiService from "@/services/ApiService";
import { ShoppingCart } from "@/models/ShoppingCart";

Vue.use(Vuex);

export const CART_STORAGE_KEY = "cart";
export const ORDER_DETAIL_STORAGE_KEY = "order";

export default new Vuex.Store({
  state: {
    categories: [],
    selectedCategoryName: "War",
    selectedCategoryBooks: [],
    cart: new ShoppingCart(),
    orderDetails: null
  },
  mutations: {
    SET_CATEGORIES(state, newCategories) {
      state.categories = newCategories;
    },
    SELECT_CATEGORY(state, categoryName) {
      state.selectedCategoryName = categoryName;
    },
    SET_SELECTED_CATEGORY_BOOKS(state, categoryBooks) {
      state.selectedCategoryBooks = categoryBooks;
    },
    ADD_TO_CART(state, book) {
      state.cart.addItem(book, 1);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    UPDATE_CART(state, { book, quantity }) {
      state.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SET_CART(state, shoppingCart) {
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(shoppingCart));
      let newCart = new ShoppingCart();
      shoppingCart.items.forEach(item => {
        newCart.addItem(item.book, item.quantity);
      });
      state.cart = newCart;
    },
    CLEAR_CART(state) {
      state.cart.clear();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SET_ORDER_DETAILS(state, orderDetails) {
      localStorage.setItem(
        ORDER_DETAIL_STORAGE_KEY,
        JSON.stringify(orderDetails)
      );
      let newOrderDetails = null;
      newOrderDetails = orderDetails;
      state.orderDetails = newOrderDetails;
    },
    CLEAR_ORDER_DETAILS(state) {
      state.orderDetails = null;
      localStorage.setItem(
        ORDER_DETAIL_STORAGE_KEY,
        JSON.stringify(this.state.orderDetails)
      );
    }
  },
  actions: {
    fetchCategories: context => {
      ApiService.fetchCategories()
        .then(json => context.commit("SET_CATEGORIES", json))
        .catch(reason => console.log("Error: " + reason));
    },
    selectCategory(context, categoryName) {
      context.commit("SELECT_CATEGORY", categoryName);
    },
    fetchSelectedCategoryBooks: context => {
      ApiService.fetchSelectedCategoryBooks(context.state.selectedCategoryName)
        .then(json => context.commit("SET_SELECTED_CATEGORY_BOOKS", json))
        .catch(reason => console.log("Error: " + reason));
    },
    addToCart(context, book) {
      context.commit("ADD_TO_CART", book);
    },
    updateCart(context, updateDetails) {
      context.commit("UPDATE_CART", updateDetails);
    },
    clearCart(context) {
      context.commit("CLEAR_CART");
    },
    clearOrderDetails(context) {
      context.commit("CLEAR_ORDER_DETAILS");
    },
    placeOrder(context, customerForm) {
      return ApiService.placeOrder({
        cart: context.state.cart,
        customerForm: customerForm
      }).then(orderDetails => {
        context.commit("CLEAR_ORDER_DETAILS");
        context.commit("CLEAR_CART");
        context.commit("SET_ORDER_DETAILS", orderDetails);
      });
    }
  }
});

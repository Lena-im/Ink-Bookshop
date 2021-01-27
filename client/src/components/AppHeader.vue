<template>
  <header>
    <div id="leftHeader">
      <div id="logo">
        <router-link :to="{ name: 'home' }">
          <img
            src="../assets/images/site/text.png"
            alt="The ink bookstore logo text"
            class="textImage"
          />
          <img
            src="../assets/images/site/logo.png"
            alt="The ink bookstore logo"
            class="logoImage"
          />
        </router-link>
      </div>
    </div>

    <div id="midHeader">
      <header-category-dropdown></header-category-dropdown>
      <header-search-bar></header-search-bar>
    </div>

    <div id="rightHeader">
      <div id="loginButton">
        <img src="../assets/images/site/login-icon.png" alt="login icon" />
      </div>

      <div id="cartButton">
        <router-link :to="{ name: 'cart' }"
          ><img
            src="../assets/images/site/cart-icon.png"
            alt="Cart Icon"
            style=""
          />
          <div id="cartCount">
            <router-link :to="{ name: 'cart' }"
              ><span style="color: #edf2f4"
                >({{ $store.state.cart.numberOfItems }})</span
              ></router-link
            >
          </div>
        </router-link>
      </div>
    </div>
  </header>
</template>

<script>
import HeaderSearchBar from "@/components/HeaderSearchBar";
import HeaderCategoryDropdown from "@/components/HeaderCategoryDropdown";
export default {
  name: "AppHeader",
  components: { HeaderSearchBar, HeaderCategoryDropdown },
  component: {
    HeaderSearchBar,
    HeaderCategoryDropdown
  }
};
</script>

<style scoped>
header {
  background: black;
  height: 150px;
  display: flex;
  justify-content: space-between;
}

p {
  padding-top: 20px;
}

#leftHeader {
  width: 200px;
}

#logo {
  padding-left: 10px;
}

.textImage {
  height: 120px;
  padding-top: 20px;
  padding-left: 20px;
}

.logoImage {
  height: 65px;
  position: relative;
  top: -70px;
  left: 215px;
}

#midHeader {
  min-width: 450px;
  display: flex;
  justify-content: space-around;
  align-items: center;
}

#rightHeader {
  width: 200px;
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding-right: 30px;
}

#loginButton {
  width: 50px;
  cursor: pointer;
}

#loginButton img {
  height: 40px;
}

#loginButton img:hover {
  height: 45px;
}

#cartButton {
  position: relative;
  cursor: pointer;
  padding-right: 40px;
}

#cartButton img {
  width: 45px;
  height: 40px;
}

#cartCount {
  position: absolute;
  font-size: 28px;
  top: 6px;
  left: 55px;
}

#cartCount:hover {
  font-size: 33px;
}
</style>

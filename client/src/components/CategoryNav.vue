<template>
  <nav id="categoryNav">
    <span id="shopByCategory">SHOP BY CATEGORY</span>
    <img
      src="../assets/images/site/categoryFrame.png"
      id="categoryFrame"
      alt="category frame"
      style="height: 520px; width: 280px;"
    />

    <ul id="categoryButtons">
      <template v-for="category in $store.state.categories">
        <li
          v-if="$store.state.selectedCategoryName === category.name"
          :key="category.categoryId"
          class="categoryButton"
        >
          <router-link
            class="selectedCategoryLink"
            :to="{ name: 'category', params: { name: category.name } }"
          >
            <span class="selectedLabel">
              <img
                src="../assets/images/site/nav-black.png"
                class="categoryBackground"
                alt="selected category background"
                style="height: 40px; width: 250px;"
              />
              {{ category.name }}</span
            >
          </router-link>
        </li>
        <li :key="category.categoryId" v-else class="categoryButton">
          <router-link
            class="unselectedCategoryLink"
            :to="{ name: 'category', params: { name: category.name } }"
          >
            <img
              src="../assets/images/site/nav-gray.png"
              class="categoryBackground"
              alt="unselected category background"
              style="height: 40px; width: 250px;"
            />
            {{ category.name }}
          </router-link>
        </li>
      </template>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "CategoryNav"
};
</script>

<style scoped>
#categoryNav {
  display: flex;
  position: relative;
}

#shopByCategory {
  position: absolute;
  top: 80px;
  left: 80px;
  color: gray;
  font-family: AppleGothic;
}

#categoryFrame {
  padding-left: 30px;
  padding-top: 30px;
}

#categoryButtons {
  position: absolute;
  top: 120px;
  left: 60px;
  font-family: Courier;
}

.categoryButton {
  display: block;
  padding-top: 10px;
  width: 250px;
}

.categoryButton a {
  color: white;
  font-size: 30px;
  position: relative;
  top: -30px;
  left: 35px;
}
.selectedLabel {
  color: #d90429;
  font-weight: bold;
}

.categoryBackground {
  position: relative;
  top: 35px;
  left: -40px;
  z-index: -1;
}

.unselectedCategoryLink,
.selectedCategoryLink {
  display: inline-block;
}

.unselectedCategoryLink:hover,
.selectedCategoryLink:hover {
  font-weight: bold;
}
</style>

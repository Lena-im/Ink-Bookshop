<template>
  <div id="categoryDropdown">
    <router-link
      :to="{
        name: 'category',
        params: { name: $store.state.selectedCategoryName }
      }"
      id="dropdownButton"
    >
      <img
        id="dropdownImage"
        src="../assets/images/site/dropdown-icon.png"
        alt="category-drop-dwon-icon"
        style="height: 20px;"
      />
    </router-link>
    <ul id="categoryDropdownList">
      <br />

      <li
        v-for="category in $store.state.categories"
        :key="category.categoryId"
        class="categoryButton unselectedCategoryButton"
      >
        <router-link
          class="unselectedCategoryLink"
          :to="{ name: 'category', params: { name: category.name } }"
          >{{ category.name }}</router-link
        >
      </li>
      <br />
    </ul>
  </div>
</template>

<script>
export default {
  name: "HeaderCategoryDropdown"
};
</script>

<style scoped>
#categoryDropdown {
  position: relative;
  display: inline-block;
  padding-right: 10px;
}

#dropdownImage:hover {
  height: 30px;
  content: url("../assets/images/site/dropdown-icon-hover.png");
}

#categoryDropdownList {
  display: none;
  position: absolute;
  background-color: gray;
  opacity: 0.98;
  min-width: 160px;
  border-radius: 5%;
  box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
  z-index: 1;
}

#categoryDropdownList a {
  font-family: Bradley Hand;
  font-size: 25px;
  color: white;
  padding: 12px 16px;
  text-decoration: none;
  display: block;
}

#categoryDropdownList a:hover {
  background-color: #262626;
}

#categoryDropdown:hover #categoryDropdownList {
  display: block;
}
</style>

<template>
  <form id="searchForm" action="category.html">
    <input id="searchBar" type="text" placeholder="Search..." />
    <input
      id="searchIcon"
      type="image"
      :src="require('../assets/images/site/search-icon.png')"
      alt="Search Icon"
    />
  </form>
</template>

<script>
export default {
  name: "HeaderSearchBar"
};
</script>

<style scoped>
#searchForm {
  display: flex;
  justify-content: center;
}

input:focus {
  outline: none;
  color: white;
}

#searchBar {
  width: 350px;
  background-color: transparent;
  border: 0;
  border-bottom: 2px solid #ebebeb;
}

#searchIcon {
  height: 50px;
}
</style>

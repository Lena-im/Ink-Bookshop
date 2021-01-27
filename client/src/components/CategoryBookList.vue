<template>
  <ul>
    <template v-for="book in $store.state.selectedCategoryBooks">
      <category-book-list-item :key="book.bookId" :book="book">
      </category-book-list-item>
    </template>
  </ul>
</template>

<script>
import CategoryBookListItem from "./CategoryBookListItem";

export default {
  name: "CategoryBookList",
  components: {
    CategoryBookListItem
  }
};
</script>

<style scoped>
ul {
  padding: 15px;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}
</style>

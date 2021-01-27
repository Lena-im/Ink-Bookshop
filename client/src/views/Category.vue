<template>
  <div id="category">
    <div id="categoryView">
      <div id="categoryNav">
        <category-nav></category-nav>
      </div>
      <div id="categoryBooks">
        <category-book-list> </category-book-list>
      </div>
    </div>
  </div>
</template>

<script>
import CategoryNav from "@/components/CategoryNav";
import CategoryBookList from "@/components/CategoryBookList";
export default {
  name: "category",
  components: {
    CategoryNav,
    CategoryBookList
  },
  created: function() {
    this.$store.dispatch("selectCategory", this.$route.params.name);
    this.$store.dispatch("fetchSelectedCategoryBooks");
  }
};
</script>

<style scoped>
#categoryView {
  display: flex;
  flex-direction: row;
  justify-content: space-around;
}

#categoryNav {
  padding: 10px 5px 20px 10px;
}

#categoryBooks {
  display: flex;
  justify-content: center;
  width: 900px;
  padding: 30px 30px 30px 10px;
}
</style>

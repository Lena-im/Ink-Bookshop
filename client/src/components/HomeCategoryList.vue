<template>
  <div id="categoryList">
    <ul id="suggestedBookList">
      <template v-for="book in suggestedBooks">
        <category-book-list-item :key="book.bookId" :book="book">
        </category-book-list-item>
      </template>
    </ul>
  </div>
</template>

<script>
import CategoryBookListItem from "./CategoryBookListItem";

export default {
  name: "HomeCategoryList",
  data: function() {
    return {
      suggestedBooks: [
        {
          bookId: 1020,
          title: "Five Dynasties and Ten States",
          imageFile: "five-dynasties and-ten-states.png",
          author: "by Feng Sun",
          price: 1061,
          isPublic: true,
          categoryId: 1005
        },
        {
          bookId: 1001,
          title: "Romance of Three Kingdoms",
          imageFile: "romance-of-three-kingdoms.png",
          author: "by Guanzhong Luo",
          price: 799,
          isPublic: true,
          categoryId: 1001
        },
        {
          bookId: 1014,
          title: "Pre-Qin Scholars",
          imageFile: "pre-qin-scholars.png",
          author: "by Zhongtian Yi",
          price: 2157,
          isPublic: true,
          categoryId: 1004
        },
        {
          bookId: 1017,
          title: "The History of Sui&Tang",
          imageFile: "the-history-of-sui&tang.png",
          author: "by Zhongqian",
          price: 957,
          isPublic: true,
          categoryId: 1005
        }
      ]
    };
  },
  components: {
    CategoryBookListItem
  }
};
</script>

<style scoped>
#suggestedBookList {
  display: flex;
  flex-wrap: wrap;
  padding-top: 30px;
}

.suggestedBookListItem .bookButtons a {
  color: black;
  display: flex;
  align-items: center;
}

.suggestedBookListItem .bookButtons a:hover {
  color: #c00000;
}

.suggestedBookListItem .bookButtons img {
  padding-top: 5px;
  padding-right: 10px;
  padding-bottom: 5px;
}
</style>

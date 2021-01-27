const apiUrl =
  location.protocol +
  "//" +
  location.hostname +
  ":8080" +
  process.env.BASE_URL +
  "api";

export default {
  fetchCategories() {
    const url = apiUrl + "/categories";
    return fetch(url)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Network response was not ok");
      })
      .catch(reason => {
        console.log("Error fetching category data: ", reason);
      });
  },
  fetchSelectedCategoryBooks(category) {
    const url = apiUrl + "/categories/name/" + category + "/books";
    return fetch(url)
      .then(response => {
        if (response.ok) {
          return response.json();
        }
        throw new Error("Network response was not ok");
      })
      .catch(reason => {
        console.log("Error fetching category books data: ", reason);
      });
  },
  placeOrder(order) {
    console.log("POSTing to " + `${apiUrl}/orders`);
    return fetch(`${apiUrl}/orders`, {
      method: "POST",
      body: JSON.stringify(order),
      headers: {
        "Content-Type": "application/json"
      }
    }).then(stream => {
      if (stream.ok) {
        return stream.json();
      }
      throw new Error("Network response was not ok.");
    });
  }
};

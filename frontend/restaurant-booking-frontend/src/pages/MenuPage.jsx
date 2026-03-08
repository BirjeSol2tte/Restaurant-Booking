import { useEffect, useState } from "react";
import api from "../api/api";

export default function MenuPage() {
  const [selectedDate, setSelectedDate] = useState(
    new Date().toISOString().split("T")[0]
  );
  const [dishOfTheDay, setDishOfTheDay] = useState(null);

  const [categories, setCategories] = useState([]);
  const [selectedCategory, setSelectedCategory] = useState("");
  const [meals, setMeals] = useState([]);
  const [menuLoading, setMenuLoading] = useState(false);

  useEffect(() => {
    api.get("/menu/dish-of-the-day", {
      params: {
        date: selectedDate
      }
    })
      .then((response) => {
        setDishOfTheDay(response.data);
      })
      .catch((error) => {
        console.error("Error fetching dish of the day:", error);
      });
  }, [selectedDate]);

  useEffect(() => {
    fetch("https://www.themealdb.com/api/json/v1/1/categories.php")
      .then((response) => response.json())
      .then((data) => {
        const loadedCategories = data.categories || [];
        setCategories(loadedCategories);

        if (loadedCategories.length > 0) {
          setSelectedCategory(loadedCategories[0].strCategory);
        }
      })
      .catch((error) => {
        console.error("Error fetching TheMealDB categories:", error);
      });
  }, []);

  useEffect(() => {
    if (!selectedCategory) return;

    setMenuLoading(true);

    fetch(
      `https://www.themealdb.com/api/json/v1/1/filter.php?c=${encodeURIComponent(selectedCategory)}`
    )
      .then((response) => response.json())
      .then((data) => {
        setMeals((data.meals || []).slice(0, 8));
      })
      .catch((error) => {
        console.error("Error fetching meals by category:", error);
      })
      .finally(() => {
        setMenuLoading(false);
      });
  }, [selectedCategory]);

  return (
    <div className="content-stack">
      <section className="section-card">
        <div className="section-header-row">
          <div>
            <span className="section-kicker">Chef’s special</span>
            <h1 className="page-title">Menu</h1>
          </div>

          <div className="date-picker-block">
            <label>Select Date</label>
            <input
              type="date"
              value={selectedDate}
              onChange={(e) => setSelectedDate(e.target.value)}
            />
          </div>
        </div>

        {dishOfTheDay ? (
          <div className="dish-highlight">
            <div>
              <span className="section-kicker">Dish of the Day</span>
              <h2>{dishOfTheDay.name}</h2>
              <p>{dishOfTheDay.description}</p>
            </div>
          </div>
        ) : (
          <p>Loading dish of the day...</p>
        )}
      </section>

      <section className="section-card">
        <div className="section-header-row">
          <div>
            <span className="section-kicker">Inspired by TheMealDB</span>
            <h2 className="section-title">Explore More Dishes</h2>
          </div>

          <div className="date-picker-block">
            <label>Category</label>
            <select
              value={selectedCategory}
              onChange={(e) => setSelectedCategory(e.target.value)}
            >
              {categories.map((category) => (
                <option
                  key={category.idCategory}
                  value={category.strCategory}
                >
                  {category.strCategory}
                </option>
              ))}
            </select>
          </div>
        </div>

        {menuLoading ? (
          <p>Loading meals...</p>
        ) : (
          <div className="meal-grid">
            {meals.map((meal) => (
              <article key={meal.idMeal} className="meal-card">
                <img
                  src={meal.strMealThumb}
                  alt={meal.strMeal}
                  className="meal-image"
                />
                <div className="meal-card-content">
                  <h3>{meal.strMeal}</h3>
                  <p>{selectedCategory}</p>
                </div>
              </article>
            ))}
          </div>
        )}
      </section>
    </div>
  );
}
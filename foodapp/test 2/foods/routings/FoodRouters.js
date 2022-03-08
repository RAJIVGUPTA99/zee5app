import React from "react";
import { Route, Routes } from "react-router-dom";
import FoodItem from "../../foods/components/FoodItem";

const FoodRouters = () => {
  return (
    <div>
      <Routes>
        {/* <Route path="/create-food" element={}></Route> */}
        <Route path="/:id" element={<FoodItem></FoodItem>}></Route>
      </Routes>
    </div>
  );
};

export default FoodRouters;

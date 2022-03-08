import {
  CLEAR_FOOD,
  FOOD_ERROR,
  GET_FOOD,
  GET_FOODS,
  UPDATE_FOOD,
} from "../../../redux/types/foodTypes";
import api from "../../../utils/api";
import { setAlert } from "../../core/actions/alertAction";

// Get all foods
export const getFoods = () => async (dispatch) => {
  dispatch({ type: CLEAR_FOOD });

  try {
    const res = await api.get("/food/");

    dispatch({
      type: GET_FOODS,
      payload: res.data,
    });
  } catch (err) {
    dispatch({
      type: FOOD_ERROR,
      payload: { msg: err.response.statusText, status: err.response.status },
    });
  }
};

//change

// Get food by ID
export const getFoodById = (foodId) => async (dispatch) => {
  try {
    const res = await api.get(`/food/${foodId}`);

    dispatch({
      type: GET_FOOD,
      payload: res.data,
    });
  } catch (err) {
    dispatch({
      type: FOOD_ERROR,
      payload: { msg: err.response.statusText, status: err.response.status },
    });
  }
};

// Add Food
export const addFood = (formData, navigate) => async (dispatch) => {
  try {
    const res = await api.put("/food/addFood", formData);

    dispatch({
      type: UPDATE_FOOD,
      payload: res.data,
    });

    dispatch(setAlert("Food Added", "success"));

    navigate("/");
  } catch (err) {
    const errors = err.response.data.errors;

    if (errors) {
      errors.forEach((error) => dispatch(setAlert(error.msg, "danger")));
    }

    dispatch({
      type: FOOD_ERROR,
      payload: { msg: err.response.statusText, status: err.response.status },
    });
  }
};

// Delete Food
export const deleteFood = (foodId) => async (dispatch) => {
  try {
    const res = await api.delete(`/food/addFood/${foodId}`);

    dispatch({
      type: UPDATE_FOOD,
      payload: res.data,
    });

    dispatch(setAlert("Food Deleted", "success"));
  } catch (err) {
    dispatch({
      type: FOOD_ERROR,
      payload: { msg: err.response.statusText, status: err.response.status },
    });
  }
};

// Edit or update Food

// Get food by type

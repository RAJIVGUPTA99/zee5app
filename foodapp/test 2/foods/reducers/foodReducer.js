import {
  ADD_FOOD,
  CLEAR_FOOD,
  FOOD_DELETED,
  FOOD_ERROR,
  GET_FOOD,
  GET_FOODS,
  GET_FOODS_BY_TYPE,
  UPDATE_FOOD,
} from "../../../redux/types/foodTypes";

const initialState = {
  food: null,
  foods: [],
  loading: true,
  error: {},
  errors: {},
};

function foodReducer(state = initialState, action) {
  const { type, payload } = action;

  switch (type) {
    case GET_FOODS:
      return {
        ...state,
        foods: payload,
        loading: false,
      };

    case CLEAR_FOOD:
      return {
        ...state,
        food: null,
      };

    // change

    case ADD_FOOD:
      return {
        ...state,
        food: payload,
        loading: false,
      };

    case GET_FOOD:
      return {
        ...state,
        food: payload,
        loading: false,
      };
    case UPDATE_FOOD:
      return {
        ...state,
        food: payload,
        loading: false,
      };

    case FOOD_DELETED:
      return {
        ...state,
        food: payload,
        loading: false,
      };

    case FOOD_ERROR:
      return {
        ...state,
        error: payload,
        loading: false,
        food: null,
      };

    case GET_FOODS_BY_TYPE:
      return {
        ...state,
        foods: payload,
        loading: false,
      };

    default:
      return state;
  }
}

export default foodReducer;

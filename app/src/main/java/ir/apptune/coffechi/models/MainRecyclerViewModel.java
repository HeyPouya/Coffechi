package ir.apptune.coffechi.models;

public class MainRecyclerViewModel {
    String id;
    String restaurantName;
    String restaurantImage;
    String restaurantPriceRange;
    String distance;
    String restaurantRate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getRestaurantRate() {
        return restaurantRate;
    }

    public void setRestaurantRate(String restaurantRate) {
        this.restaurantRate = restaurantRate;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantImage() {
        return restaurantImage;
    }

    public void setRestaurantImage(String restaurantImage) {
        this.restaurantImage = restaurantImage;
    }

    public String getRestaurantPriceRange() {
        return restaurantPriceRange;
    }

    public void setRestaurantPriceRange(String restaurantPriceRange) {
        this.restaurantPriceRange = restaurantPriceRange;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}

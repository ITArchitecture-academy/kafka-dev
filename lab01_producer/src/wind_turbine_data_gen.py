import random
import time


class WindTurbineDataGen:
    msgs_per_sec = 0
    turbines = []
    msg_count = 0

    def __init__(self, num_turbines, msgs_per_sec):
        self.msgs_per_sec = msgs_per_sec
        for i in range(num_turbines):
            self.turbines.append({
                "max_power": random.uniform(5000, 1.5 * 1000 * 1000),
                "id": "Turbine{}".format(i),
                "park_id": "Windpark{}".format(i % 5)
            })

    def __iter__(self):
        return self

    def __next__(self):
        return self.next()

    def next(self):
        turbine_config = random.choice(self.turbines)
        turbine_data = {
            "wind_turbine_id": turbine_config["id"],
            "wind_park_id": turbine_config["park_id"],
            "current_power": random.uniform(0, turbine_config["max_power"])
        }

        if self.msg_count >= self.msgs_per_sec and self.msgs_per_sec != -1:
            time.sleep(1)
            self.msg_count = 0
        self.msg_count += 1
        return turbine_data

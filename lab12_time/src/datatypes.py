import faust


class SlidingAverage(faust.Record, serializer='json'):
    sum: float
    count: int
    average: float

    @staticmethod
    def init():
        return SlidingAverage(sum=0, count=0, average=0)

    def add(self, value: float):
        sum = self.sum + value
        count = self.count + 1
        average = sum / count
        return SlidingAverage(sum=sum, count=count, average=average)


class WindTurbineData(faust.Record, serializer='json'):
    wind_turbine_id: str
    wind_park_id: str
    current_power: float

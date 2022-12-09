import faust


class WindTurbineData(faust.Record, serializer='json'):
    wind_turbine_id: str
    wind_park_id: str
    current_power: float


class WindParkMasterData(faust.Record, serializer='json'):
    id: int
    name: str


class CustomerMasterData(faust.Record, serializer='json'):
    id: int
    name: str
    price_per_mwh: float


class WindTurbineMasterData(faust.Record, serializer='json'):
    id: int
    customer_id: int
    wind_park_id: int
    customer_name: str = None
    wind_park_name: str = None


class WindTurbineDataWithName(faust.Record, serializer='json'):
    wind_turbine_id: str
    wind_park_id: str
    wind_park_name: str
    current_power: float

import multiprocessing

import bottle

measurements = None
manager = None
process = None


@bottle.route('/metrics', method='GET')
def metrics():
    global measurements
    metrics_str = """# HELP power The current power of a wind turbine in W
# TYPE power counter
"""
    for data in measurements.values():
        metrics_str += "{id}{{turbine={id},windPark={park}}} {power:.2f}\n" \
            .format(id=data["wind_turbine_id"],
                    park=data["wind_park_id"],
                    power=data["current_power"])
    bottle.response.content_type = 'text/plain'
    return metrics_str


def add_measurement(key, value):
    global measurements
    measurements[key] = value


def start_internal(local_measurements):
    global measurements
    measurements = local_measurements
    bottle.run(port=8989, host='localhost', debug=True)


def start():
    global process
    global measurements
    global manager
    manager = multiprocessing.Manager()
    measurements = manager.dict()
    process = multiprocessing.Process(target=start_internal, args=[measurements])
    process.daemon = True
    process.start()

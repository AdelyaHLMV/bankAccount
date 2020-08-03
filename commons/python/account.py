import sys
import requests
import time
import datetime
import os

def test(number, log_file):
    log_file.write('test_'+number+'\n')
    json_request = {
              "accountNumber": "1",
              "bankroll": "12"
            }

    request_header = {
        "content-type": "application/json; charset=UTF-8"
    }

    try:
        r = requests.post('http://localhost:10030/bank/account/replenish', json=json_request, headers=request_header)
        r.raise_for_status()
        log_file.write('HTTP status success code ' + str(r.status_code) + '\n')
    except requests.exceptions.Timeout:
        log_file.write('ERROR: ' + e + '\n')
    except requests.exceptions.TooManyRedirects:
        log_file.write('ERROR: ' + e + '\n')
    except requests.exceptions.RequestException as e:
        log_file.write('ERROR: ' + e + '\n')


def run_timer(number, delay, run_count):
    log_filename = os.getcwd() + '/log/' + number + '.txt'
    log_folder = os.path.dirname(log_filename)
    if not os.path.exists(log_folder):
        os.makedirs(log_folder)

    log_file = open(log_filename, 'w', 1)
    log_file.write('OPEN\n\n')

    for num in range(0, run_count):
        next_time = time.time() + delay
        now = datetime.datetime.now()
        log_file.write(now.strftime("%d-%m-%Y %H:%M:%S\n"))
        try:
            test(number, log_file)
        except Exception:
            log_file.write('Error\n')
        time.sleep(max(0, next_time - time.time()))
    log_file.write('\nCLOSE')
    log_file.close()

if __name__ == '__main__':
    run_timer(sys.argv[1], int(sys.argv[2]), int(sys.argv[3]))



import 'package:intl/intl.dart';

class HumanFormats{

  static String formatDate(DateTime date) => DateFormat('dd-MM-yyyy').format(date);

  static DateTime formatStringToDate(String date) => DateFormat('dd/MM/yyyy').parse(date);

}
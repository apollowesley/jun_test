<?php  if ( ! defined('BASEPATH')) exit('No direct script access allowed');
/*
| -------------------------------------------------------------------------
| URI ROUTING
| -------------------------------------------------------------------------
| This file lets you re-map URI requests to specific controller functions.
|
| Typically there is a one-to-one relationship between a URL string
| and its corresponding controller class/method. The segments in a
| URL normally follow this pattern:
|
|	example.com/class/method/id/
|
| In some instances, however, you may want to remap this relationship
| so that a different class/function is called than the one
| corresponding to the URL.
|
| Please see the user guide for complete details:
|
|	http://codeigniter.com/user_guide/general/routing.html
|
| -------------------------------------------------------------------------
| RESERVED ROUTES
| -------------------------------------------------------------------------
|
| There area two reserved routes:
|
|	$route['default_controller'] = 'welcome';
|
| This route indicates which controller class should be loaded if the
| URI contains no data. In the above example, the "welcome" class
| would be loaded.
|
|	$route['404_override'] = 'errors/page_missing';
|
| This route will tell the Router what URI segments to use if those provided
| in the URL cannot be matched to a valid route.
|
*/

$route['default_controller'] = "tutorship/all";
$route['404_override'] = 'page_not_found';

$route["tutorships"] = "tutorship/all";
$route["tutorship"] = "tutorship/detail";
$route["students"] = "student/all";
$route["student"] = "student/detail";
$route["teachers"] = "teacher/all";
$route["teacher"] = "teacher/detail";

$route['admin'] = 'admin/login';
$route['admin/index'] = 'admin/login';
$route["admin/tutorships"] = "admin/tutorship/all";
$route["admin/tutorship"] = "admin/tutorship/detail";
$route['admin/students'] = 'admin/student/all';
$route['admin/student'] = 'admin/student/detail';
$route['admin/teachers'] = 'admin/teacher/all';
$route['admin/teacher'] = 'admin/teacher/detail';

$route["admin/enterprise/entry/(:num)/type/json"] = "admin/enterprise/entry";

$route["user"] = "user/login";
$route["user/tutorships"] = "user/tutorship/all";

$route["students"] = "student/page";


/* End of file routes.php */
/* Location: ./application/config/routes.php */
// equations.h

#define g 9.81

double vel(double v_burn, double m, double k, double t);

double alt(double y_burn, double v_burn, double m, double k, double t);

double t_a(double v_burn, double m, double k);

double T(double mass, double velocity);

double V(double mass, double altitude);

void wait(double seconds);

void print_splash();

void load(int cycles);

$DIFF
float calcDiff(vec3 normal, vec3 direction) {
	return max(0, dot(normal, -direction));
}
$END

$SPEC
#define BLINN_PHONG
float calcSpec(vec3 toCamera, vec3 direction, vec3 normal, float shineDamper, float strength) {
	#ifndef BLINN_PHONG
		vec3 lightOut = normalize(reflect(direction, normal));
		return pow(max(0, dot(toCamera, lightOut)), shineDamper) * strength;
	#else
		vec3 halfway = normalize(toCamera - direction);
		return pow(max(0, dot(normal, halfway)), shineDamper) * strength;
	#endif
}
$END

$ATTEN
float calcAtten(vec3 atten, float distance) {
	return atten.x * distance * distance + atten.y * distance + atten.z;
}
$END
